object Deps {
    object Android {
        object Compose {
            private const val composeVersion = "1.3.1"
            private const val materialAdapterVersion = "1.2.1"
            private const val lifecycleVersion = "2.6.0-beta01"

            const val runtime = "androidx.compose.runtime:runtime:$composeVersion"
            const val ui = "androidx.compose.ui:ui:$composeVersion"
            const val materialAdapter = "com.google.android.material:compose-theme-adapter:$materialAdapterVersion"
            const val uiTooling = "androidx.compose.ui:ui-tooling:$composeVersion"
            const val preview = "androidx.compose.ui:ui-tooling-preview:$composeVersion"
            const val foundation = "androidx.compose.foundation:foundation:$composeVersion"
            const val foundationLayout = "androidx.compose.foundation:foundation-layout:$composeVersion"
            const val material = "androidx.compose.material:material:$composeVersion"
            const val lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion"
        }

        object Activity {
            private const val actVersion = "1.3.1"

            const val compose = "androidx.activity:activity-compose:$actVersion"
        }

        object Glide {
            private const val glideComposeVersion = "1.0.0-alpha.1"

            const val glideCompose = "com.github.bumptech.glide:compose:$glideComposeVersion"
        }

        object Navigation {
            private const val navVersion = "2.5.3"

            const val navigation = "androidx.navigation:navigation-compose:$navVersion"
        }

        object Paging {
            private const val paging_version = "3.1.1"

            const val runtime = "androidx.paging:paging-runtime:$paging_version"
            const val compose = "androidx.paging:paging-compose:1.0.0-alpha18"
        }
    }

    object Kotlin {
        object Serialization {
            private const val serializationVersion = "1.5.0"

            const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
        }
    }

    object Timber {
        private const val timberVersion = "5.0.1"

        const val timber = "com.jakewharton.timber:timber:$timberVersion"
    }

    object Ktor {
        private const val ktorVersion = "2.2.3"

        const val core = "io.ktor:ktor-client-core:$ktorVersion"
        const val cio = "io.ktor:ktor-client-cio:$ktorVersion"
        const val json = "io.ktor:ktor-client-json:$ktorVersion"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
        const val logging = "io.ktor:ktor-client-logging:$ktorVersion"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
        const val okhttp = "io.ktor:ktor-client-okhttp:$ktorVersion"
    }

    object Coroutines {
        private const val coroutinesVersion = "1.6.4"

        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object Koin {
        private const val koinVersion = "3.3.3"
        private const val koinComposeVersion = "3.4.2"

        const val core = "io.insert-koin:koin-core:$koinVersion"
        const val android = "io.insert-koin:koin-android:$koinVersion"
        const val androidCompose = "io.insert-koin:koin-androidx-compose:$koinComposeVersion"
    }

    object Room {
        private const val room_version = "2.5.1"

        const val runtime = "androidx.room:room-runtime:$room_version"
        const val compiler = "androidx.room:room-compiler:$room_version"
        const val ktx = "androidx.room:room-ktx:$room_version"
        const val paging = "androidx.room:room-paging:$room_version"
    }
}