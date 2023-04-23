plugins {
    kotlin("kapt") version "1.8.20"
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization").version("1.8.20").apply(false)
}

android {
    namespace = "com.pavellukyanov.androidgym"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pavellukyanov.androidgym"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(project(":domain"))

    //Serialization
    implementation(Deps.Kotlin.Serialization.serialization)

    //Coroutines
    implementation(Deps.Coroutines.core)

    //Koin
    implementation(Deps.Koin.core)
    implementation(Deps.Koin.android)

    //Ktor
    api(Deps.Ktor.core)
    implementation(Deps.Ktor.cio)
    implementation(Deps.Ktor.json)
    implementation(Deps.Ktor.serialization)
    implementation(Deps.Ktor.logging)
    implementation(Deps.Ktor.contentNegotiation)
    implementation(Deps.Ktor.okhttp)

    //Timber
    implementation(Deps.Timber.timber)

    //Room
    implementation(Deps.Room.runtime)
    implementation(Deps.Room.ktx)
    implementation(Deps.Room.paging)
    kapt(Deps.Room.compiler)
}