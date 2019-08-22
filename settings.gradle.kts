import java.net.URI

rootProject.name = "JetBrains-Discord-Integration-Bot"

pluginManagement {

    repositories {
        gradlePluginPortal()
        maven {
            url = URI("http://palantir.bintray.com/releases")
        }
    }

    plugins {
        kotlin("jvm") version "1.3.41"
        id("com.github.ben-manes.versions") version "0.22.0"
        id("com.github.johnrengelman.shadow") version "5.1.0"
        id("com.palantir.consistent-versions") version "1.10.0"
        id("com.palantir.baseline-exact-dependencies") version "1.12.0"
    }
}
