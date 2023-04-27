val serialization_version: String by project
val koin_version: String by project
val timber_version: String by project
val desugar_version: String by project

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.pavellukyanov.domain"
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
    implementation(project(":data"))
    implementation(project(":utils"))
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:$desugar_version")

    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")

    //Koin
    implementation("io.insert-koin:koin-core:$koin_version")

    //Timber
    implementation("com.jakewharton.timber:timber:$timber_version")
}