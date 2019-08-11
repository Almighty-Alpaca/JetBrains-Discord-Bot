import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.3.41"
    id("com.google.cloud.tools.jib") version "1.4.0"
    id("com.github.ben-manes.versions") version "0.22.0"
    id("com.github.johnrengelman.shadow") version "5.1.0"
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
    compile(kotlin(module = "stdlib"))
    compile(kotlin(module = "script-runtime"))
    compile(kotlin(module = "script-util"))
    compile(kotlin(module = "compiler-embeddable"))

    compile(group = "com.jagrosh", name = "jda-utilities-command", version = "3.0.1")
    compile(group = "com.jagrosh", name = "jda-utilities-menu", version = "3.0.1")
    compile(group = "net.dv8tion", name = "JDA", version = "4.0.0_39") {
        exclude(group = "club.minnced", module = "opus-java")
    }

    compile(group = "com.uchuhimo", name = "konf", version = "0.13.3")
    compile(group = "org.apache.commons", name = "commons-lang3", version = "3.9")
    compile(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
}

val secrets = file("secrets.gradle.kts")
if (secrets.exists()) {
    apply(from = secrets)

    jib {
        from {
            image = "openjdk@sha256:1d250ad181da7a145504a11b6c02d2a39ac55a9510a51b353950c124e9987772" // directly point at the arm image

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
        gradleReleaseChannel = "current"

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
        gradleVersion = "5.5.1"
    }

    filter { task -> task.name.startsWith("jib") }
        .onEach { task -> task.group = "docker" }
}
