plugins {
    kotlin("kapt") version "1.8.20"
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization").version("1.8.20").apply(false)
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(project(":data"))

    //Serialization
    implementation(Deps.Kotlin.Serialization.serialization)

    //Koin
    implementation(Deps.Koin.core)

    //Timber
    implementation(Deps.Timber.timber)

    //Room
    implementation(Deps.Room.runtime)
    implementation(Deps.Room.ktx)
    implementation(Deps.Room.paging)
    kapt(Deps.Room.compiler)
}