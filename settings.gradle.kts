rootProject.name = "JetBrains-Discord-Integration-Bot"

pluginManagement {
    plugins {
        kotlin("jvm") version "1.3.41"
        id("com.google.cloud.tools.jib") version "1.4.0"
        id("com.github.ben-manes.versions") version "0.22.0"
        id("com.github.johnrengelman.shadow") version "5.1.0"
        id("com.palantir.consistent-versions") version "1.9.2"
    }
}
