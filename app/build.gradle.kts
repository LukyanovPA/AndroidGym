plugins {
    id("com.android.application")
    kotlin("android")
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

dependencies {
    implementation(project(":domain"))
    //Android
    implementation(Deps.Android.Compose.ui)
    implementation(Deps.Android.Compose.uiTooling)
    implementation(Deps.Android.Compose.preview)
    implementation(Deps.Android.Compose.foundation)
    implementation(Deps.Android.Compose.material)
    implementation(Deps.Android.Compose.lifecycle)
    implementation(Deps.Android.Compose.runtime)
    implementation(Deps.Android.Compose.foundationLayout)
    implementation(Deps.Android.Compose.materialAdapter)
    implementation(Deps.Android.Activity.compose)
    implementation(Deps.Coroutines.android)
    implementation(Deps.Android.Navigation.navigation)

    //Kotlin
    implementation(Deps.Kotlin.Serialization.serialization)

    //Koin
    implementation(Deps.Koin.core)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.androidCompose)

    //Timber
    implementation(Deps.Timber.timber)

    //Glide
    implementation(Deps.Android.Glide.glideCompose)

    //Paging
    implementation(Deps.Android.Paging.runtime)
    implementation(Deps.Android.Paging.compose)
}