val compose_version: String by project
val material_dapter_version: String by project
val compose_lifecycle_version: String by project
val coroutines_version: String by project
val nav_version: String by project
val serialization_version: String by project
val koin_version: String by project
val koin_compose_version: String by project
val timber_version: String by project
val desugar_version: String by project
val rich_editor: String by project
val systemuicontroller: String by project
val app_metrica: String by project

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.pavellukyanov.androidgym.app"
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

        debug {
            isDebuggable = true
            isJniDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))

    //Android
    val composeBom = platform("androidx.compose:compose-bom:2023.04.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$compose_lifecycle_version")
    implementation("androidx.compose.runtime:runtime:$compose_version")
    implementation("androidx.compose.foundation:foundation-layout:$compose_version")
    implementation("com.google.android.material:compose-theme-adapter:$material_dapter_version")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version")
    implementation("androidx.navigation:navigation-compose:$nav_version")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugar_version")

    // Google
    val crash = platform("com.google.firebase:firebase-bom:32.1.0")
    implementation(crash)
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    //Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$systemuicontroller")

    //Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")

    //Koin
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")
    implementation("io.insert-koin:koin-androidx-compose:$koin_compose_version")

    //Timber
    implementation("com.jakewharton.timber:timber:$timber_version")

    //RichEditor
    implementation("com.mohamedrejeb.richeditor:richeditor-compose:$rich_editor")

    //Yandex AppMetrica
    implementation("com.yandex.android:mobmetricalib:$app_metrica")
}