import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.InputStream
import java.io.OutputStream

plugins {
    application
    kotlin("jvm")
    id("com.github.ben-manes.versions")
    id("com.github.johnrengelman.shadow")
    id("com.palantir.consistent-versions")
    id("com.palantir.baseline-exact-dependencies")
}

group = "com.almightyalpaca.discord.bot.jetbrains"
version = "1.0.0"

application {
    mainClassName = "com.almightyalpaca.discord.bot.jetbrains.MainKt"
}

repositories {
    jcenter()
}

dependencies {
    // Kotlin standard library
    implementation(kotlin(module = "stdlib"))

    // implementation script engine
    implementation(kotlin(module = "script-util"))
    implementation(kotlin(module = "compiler-embeddable"))
    implementation(kotlin(module = "scripting-compiler-embeddable"))

    // JDA (without audio)
    implementation(group = "net.dv8tion", name = "JDA") {
        exclude(group = "club.minnced", module = "opus-java")
    }

    // JDA-Utilities
    implementation(group = "com.jagrosh", name = "jda-utilities-command")

    // Konf (without unused language support)
    implementation(group = "com.uchuhimo", name = "konf") {
        exclude(group = "com.moandjiezana.toml", module = "toml4j")
        exclude(group = "org.dom4j", module = "dom4j")
        exclude(group = "org.eclipse.jgit", module = "org.eclipse.jgit")

        // TODO: exclude more modules from Konf
        // exclude(group = "", module = "")
    }

    implementation(group = "com.squareup.okhttp3", name = "okhttp")

    // Logback Classic
    implementation(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
}

val shadowJar = tasks["shadowJar"] as ShadowJar

tasks {
    val dockerBuildDir = File(buildDir, "docker")

    val dockerCopy by registering(Sync::class) {
        group = "docker"
        dependsOn(shadowJar)

        from("Dockerfile")

        from(shadowJar.get().archiveFile) {
            rename { "app.jar" }
        }

        into(dockerBuildDir)
    }

    val dockerContextCreate by registering(Exec::class) {
        group = "docker"

        errorOutput = OutputStream.nullOutputStream()
        standardOutput = OutputStream.nullOutputStream()
        standardInput = InputStream.nullInputStream()

        commandLine = listOf(
            "docker", "buildx",
            "create",
            "--name", "${project.name}-Builder"
        )

        isIgnoreExitValue = true
    }

    val dockerContextUse by registering(Exec::class) {
        group = "docker"
        dependsOn(dockerContextCreate)

        commandLine = listOf(
            "docker", "buildx",
            "use", "${project.name}-Builder"
        )
    }

    create<Exec>("dockerContextDelete") {
        group = "docker"

        commandLine = listOf(
            "docker", "buildx",
            "rm", "${project.name}-Builder"
        )
    }

    val dockerPrepare by registering {
        group = "docker"

        dependsOn(dockerCopy)
        dependsOn(dockerContextUse)
    }

    create<Exec>("dockerBuild") {
        group = "docker"
        dependsOn(dockerPrepare)

        workingDir = dockerBuildDir

        commandLine = listOf(
            "docker", "buildx",
            "build",
            "--platform", "linux/amd64,linux/arm64,linux/arm/v7",
            "--tag", "almightyalpaca/jetbrains-discord-integration-bot",
            "."
        )
    }

    val dockerBuildLoad by registering(Exec::class) {
        group = "docker"
        dependsOn(dockerPrepare)

        workingDir = dockerBuildDir

        commandLine = listOf(
            "docker", "buildx",
            "build",
            // TODO: build and export multi-arch manifest as soon as Docker supports it
            // "--platform", "linux/amd64,linux/arm/v7",
            "--platform", "linux/amd64",
            "--tag", "almightyalpaca/jetbrains-discord-integration-bot",
            "--load",
            "."
        )
    }

    val dockerBuildPush by registering(Exec::class) {
        group = "docker"
        dependsOn(dockerPrepare)

        workingDir = dockerBuildDir

        commandLine = listOf(
            "docker", "buildx",
            "build",
            "--platform", "linux/amd64,linux/arm64,linux/arm/v7",
            "--tag", "almightyalpaca/jetbrains-discord-integration-bot",
            "--push",
            "."
        )
    }

    create<Exec>("dockerRun") {
        group = "docker"
        dependsOn(dockerBuildLoad)

        commandLine = listOf(
            "docker", "run",
            "--name", "${project.name}-Dev",
            "--rm",
            "--mount", "type=bind,source=${project.file("config.yaml").absolutePath},target=/config/config.yaml",
            "almightyalpaca/jetbrains-discord-integration-bot"
        )
    }

    create<Exec>("dockerRunDaemon") {
        group = "docker"
        dependsOn(dockerBuildLoad)

        commandLine = listOf(
            "docker", "run",
            "-d",
            "--name", "${project.name}-Dev",
            "--rm",
            "--mount", "type=bind,source=${project.file("config.yaml").absolutePath},target=/config/config.yaml",
            "almightyalpaca/jetbrains-discord-integration-bot"
        )
    }

    create<Exec>("dockerStatus") {
        group = "docker"

        commandLine = listOf(
            "docker", "container", "ls", "--filter", "name=${project.name}-Dev"
        )
    }

    create<Exec>("dockerStop") {
        group = "docker"

        commandLine = listOf(
            "docker", "container", "stop", "${project.name}-Dev"
        )
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    create("ci") {
        group = "ci"

        val branch by lazy { System.getenv("TRAVIS_BRANCH") }
        val isPR by lazy { System.getenv("TRAVIS_PULL_REQUEST") != "false" }

        if (branch == "master" && !isPR)
            dependsOn(dockerBuildPush)
        else
            dependsOn("test")
    }

    checkUnusedDependencies {
        // Logging backend
        ignore("ch.qos.logback", "logback-classic")

        // Kotlin compiler & scripting engine
        ignore("org.jetbrains.kotlin", "kotlin-compiler-embeddable")
        ignore("org.jetbrains.kotlin", "kotlin-script-util")
        ignore("org.jetbrains.kotlin", "kotlin-scripting-compiler-embeddable")
    }

    checkImplicitDependencies {
        // Nullability annotations
        ignore("org.jetbrains", "annotations")
    }

    create("cacheDependencies") {
        doLast {
            configurations
                .filter { conf -> conf.isCanBeResolved }
                .onEach { conf -> conf.files }
        }
    }

    dependencyUpdates {
        gradleReleaseChannel = GradleReleaseChannel.CURRENT.toString()

        resolutionStrategy {
            componentSelection {
                all {
                    sequenceOf("alpha", "beta", "rc", "cr", "m", "preview", "eap", "pr")
                        .map { qualifier -> Regex(".*[.-]$qualifier[.\\d-_]*", RegexOption.IGNORE_CASE) }
                        .any { regex -> regex.matches(candidate.version) }
                        .let { if (it) reject("snapshot") }
                }
            }
        }
    }

    withType<Wrapper> {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "5.6"
    }
}
