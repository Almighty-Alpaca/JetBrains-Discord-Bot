rootProject.name = "JetBrains-Discord-Integration-Bot"

pluginManagement {

    repositories {
        gradlePluginPortal()
        maven {
            url = java.net.URI("http://palantir.bintray.com/releases")
        }
    }

    val properties = java.util.Properties().apply {
        this.load(java.nio.file.Files.newBufferedReader(settingsDir.toPath().resolve("gradle.properties")))
    }

    val versionKotlin: String by properties
    val versionGradleVersions: String by properties
    val versionGradleShadow: String by properties
    val versionGradleConsistentVersions: String by properties
    val versionGradleExactDependencies: String by properties

    plugins {
        kotlin("jvm") version versionKotlin
        id("com.github.ben-manes.versions") version versionGradleVersions
        id("com.github.johnrengelman.shadow") version versionGradleShadow
        // id("com.palantir.consistent-versions") version "1.17.3"
        id("com.palantir.baseline-exact-dependencies") version versionGradleExactDependencies
    }
}
