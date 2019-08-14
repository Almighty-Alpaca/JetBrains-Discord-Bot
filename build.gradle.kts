import com.github.benmanes.gradle.versions.updates.gradle.GradleReleaseChannel
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    id("com.google.cloud.tools.jib")
    id("com.github.ben-manes.versions")
    id("com.github.johnrengelman.shadow")
    id("com.palantir.consistent-versions")
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
    compile(kotlin(module = "stdlib"))

    // Kotlin script engine
    compile(kotlin(module = "script-util"))
    compile(kotlin(module = "compiler-embeddable"))
    compile(kotlin(module = "scripting-compiler-embeddable"))
    compile(kotlin(module = "test-junit"))

    // JDA (without audio)
    compile(group = "net.dv8tion", name = "JDA") {
        exclude(group = "club.minnced", module = "opus-java")
    }

    // JDA-Utilities
    compile(group = "com.jagrosh", name = "jda-utilities-command", version = "3.0.1")
    compile(group = "com.jagrosh", name = "jda-utilities-menu", version = "3.0.1")

    // Konf (without unused language support)
    compile(group = "com.uchuhimo", name = "konf", version = "0.13.3") {
        exclude(group = "com.moandjiezana.toml", module = "toml4j")
        exclude(group = "org.dom4j", module = "dom4j")
        exclude(group = "org.eclipse.jgit", module = "org.eclipse.jgit")

        // TODO: exclude more modules from Konf
        // exclude(group = "", module = "")
    }

    // Apache Commons Lang 3
    compile(group = "org.apache.commons", name = "commons-lang3", version = "3.9")

    // Logback Classic
    compile(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
}

val secrets = file("secrets.gradle.kts")
if (secrets.exists()) {
    apply(from = secrets)

    jib {
        from {
            image = "adoptopenjdk@sha256:92f9133b5a90d5f17943ed2964aae56dd091f5c03601478e08d4241f66fb9cae" // directly point at the arm image

            auth {
                username = project.extra["DOCKER_BASE_USERNAME"] as String?
                password = project.extra["DOCKER_BASE_PASSWORD"] as String?
            }
        }

        to {
            image = project.extra["DOCKER_TARGET_IMAGE"] as String?

            auth {
                username = project.extra["DOCKER_TARGET_USERNAME"] as String?
                password = project.extra["DOCKER_TARGET_PASSWORD"] as String?
            }
        }

        container {
            mainClass = application.mainClassName

            environment = hashMapOf()
            environment["DOCKER"] = "true"

            useCurrentTimestamp = true
        }
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    create(name = "ci") {
        group = "ci"

        val branch by lazy { System.getenv("TRAVIS_BRANCH") }
        val isPR by lazy { System.getenv("TRAVIS_PULL_REQUEST") != "false" }

        if (branch == "master" && !isPR)
            dependsOn("jib")
        else
            dependsOn("test")
    }

    "jib" {
        dependsOn("test")
        mustRunAfter("test")
    }

    create(name = "cacheDependencies") {
        doLast {
            configurations
                .filter { conf -> conf.isCanBeResolved }
                .onEach { conf -> conf.files }
        }
    }

    dependencyUpdates {
        gradleReleaseChannel = GradleReleaseChannel.RELEASE_CANDIDATE.toString()

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
        gradleVersion = "5.6-rc-2"
    }

    filter { task -> task.name.startsWith("jib") }
        .onEach { task -> task.group = "docker" }
}
