val desugar_version: String by project
val koin_version: String by project
val timber_version: String by project

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.pavellukyanov.utils"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
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
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugar_version")

    //Koin
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-android:$koin_version")

    //Timber
    implementation("com.jakewharton.timber:timber:$timber_version")
}