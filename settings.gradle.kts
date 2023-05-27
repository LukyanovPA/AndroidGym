pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven(
            url = "https://jitpack.io"
        )
    }
}

rootProject.name = "AndroidGym"
include(":app")
include(":data")
include(":domain")
include(":utils")
