plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
    kotlin("plugin.serialization").version("1.8.20").apply(false)
    kotlin("kapt") version "1.8.20"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.withType<org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask>()
    .configureEach {
        kaptProcessJvmArgs.add("-Xmx512m")
    }

kapt {
    arguments {
        arg("key", "value")
    }
}

kapt {
    useBuildCache = false
}