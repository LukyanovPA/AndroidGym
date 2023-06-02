plugins {
    id("com.android.application").version("7.4.0").apply(false)
    id("com.android.library").version("7.4.0").apply(false)
    id("org.jetbrains.kotlin.android").version("1.8.20").apply(false)
    id("org.jetbrains.kotlin.plugin.serialization").version("1.5.0").apply(false)
    id("org.jetbrains.kotlin.kapt").version("1.8.20").apply(false)
}

buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.5")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}