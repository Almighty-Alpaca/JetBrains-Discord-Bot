import com.github.benmanes.gradle.versions.updates.DependencyUpdates
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    application
    kotlin("jvm") version "1.2.71"
    id("com.google.cloud.tools.jib") version "0.9.12"
    id("com.github.ben-manes.versions") version "0.20.0"
    id("com.github.johnrengelman.shadow") version "4.0.1"
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
    compile(kotlin(module = "stdlib", version = "1.2.71"))
    compile(kotlin(module = "script-runtime", version = "1.2.71"))
    compile(kotlin(module = "script-util", version = "1.2.71"))
    compile(kotlin(module = "compiler-embeddable", version = "1.2.71"))

    compile(group = "com.jagrosh", name = "jda-utilities-command", version = "2.1.4")
    compile(group = "com.jagrosh", name = "jda-utilities-menu", version = "2.1.4")
    compile(group = "net.dv8tion", name = "JDA", version = "3.8.1_437")

    compile(group = "com.uchuhimo", name = "konf", version = "0.11")
    compile(group = "org.apache.commons", name = "commons-lang3", version = "3.8.1")
    compile(group = "ch.qos.logback", name = "logback-classic", version = "1.2.3")
}

val secrets = file("secrets.gradle.kts")
if (secrets.exists())
{
    apply(from = secrets)
}

jib {
    from {
        image = "openjdk@sha256:1d250ad181da7a145504a11b6c02d2a39ac55a9510a51b353950c124e9987772" // directly point at the arm image
    }

    to {
        image = project.extra["DOCKER_IMAGE"] as String?

        auth {
            username = project.extra["DOCKER_USERNAME"] as String?
            password = project.extra["DOCKER_PASSWORD"] as String?
        }
    }

    container {
        environment = hashMapOf()

        environment["DOCKER"] = "true"

        useCurrentTimestamp = true
    }
}

tasks {
    "dependencyUpdates"(DependencyUpdatesTask::class) {
        resolutionStrategy {
            componentSelection {
                all {
                    val rejected = sequenceOf("alpha", "beta", "rc", "cr", "m", "preview")
                            .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-]*") }
                            .any { regex -> regex.matches(candidate.version) }
                    if (rejected)
                    {
                        reject("Release candidate")
                    }
                }
            }
        }
    }
}

tasks.filter { task -> task.name.startsWith("jib") }.onEach { task -> task.group = "docker" }