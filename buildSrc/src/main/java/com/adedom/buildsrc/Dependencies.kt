package com.adedom.buildsrc

object Versions {
    const val minSdkVersion = 24
    const val targetAndCompileVersion = 33
    const val composeCompilerVersion = "1.3.0"
}

object Dependencies {

    object Project {
        const val app = ":app"
        const val core = ":core"
        const val coreUiComponents = ":core:ui_components"
        const val featuresSplashScreen = ":features:splash_screen"
        const val featuresWelcome = ":features:welcome"
        const val featuresAuthentication = ":features:authentication"
        const val featuresMain = ":features:main"
        const val featuresFoodDetail = ":features:food_detail"
        const val featuresConnectivity = ":features:connectivity"
        const val featuresSearchFood = ":features:search_food"
        const val featuresUserProfile = ":features:user_profile"
        const val myFood = ":myfood"
        const val dataProfile = ":data:profile"
    }

    object AndroidXCore {
        private const val version = "1.9.0"
        const val coreKtx = "androidx.core:core-ktx:$version"
    }

    object AndroidXActivity {
        private const val version = "1.5.1"
        const val activityCompose = "androidx.activity:activity-compose:$version"
    }

    object AndroidGoogle {
        private const val version = "1.6.1"
        const val material = "com.google.android.material:material:$version"
    }

    object AndroidXCompose {
        private const val version = "1.2.1"
        const val material = "androidx.compose.material:material:$version"
        const val ui = "androidx.compose.ui:ui:$version"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val debugUiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val debugUiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
        const val androidTestUiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
    }

    object AndroidXNavigation {
        private const val version = "2.5.2"
        const val navigationCompose = "androidx.navigation:navigation-compose:$version"
    }

    object AndroidXDataStore {
        private const val version = "1.0.0"
        const val datastorePreferences = "androidx.datastore:datastore-preferences:$version"
    }

    object AndroidXArch {
        private const val version = "2.1.0"
        const val testCoreTesting = "androidx.arch.core:core-testing:$version"
    }

    object Accompanist {
        private const val version = "0.25.1"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
    }

    object Lifecycle {
        private const val version = "2.5.1"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
        const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
    }

    object KotlinX {
        private const val version = "1.6.4"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val testCoroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Ktor {
        private const val version = "2.1.1"
        const val clientCore = "io.ktor:ktor-client-core:$version"
        const val clientCio = "io.ktor:ktor-client-cio:$version"
        const val clientLogging = "io.ktor:ktor-client-logging:$version"
        const val clientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val serializationKotlinxJson = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val clientAuth = "io.ktor:ktor-client-auth:$version"
        const val testClientMock = "io.ktor:ktor-client-mock:$version"
    }

    object Koin {
        private const val version = "3.2.0"
        const val koinCore = "io.insert-koin:koin-core:$version"
        const val koinAndroid = "io.insert-koin:koin-android:$version"
        const val koinAndroidxCompose = "io.insert-koin:koin-androidx-compose:$version"
    }

    object SquareUp {
        private const val version = "1.5.3"
        const val sqlDelightCoroutinesExt = "com.squareup.sqldelight:coroutines-extensions:$version"
        const val sqlDelightAndroidDriver = "com.squareup.sqldelight:android-driver:$version"
    }

    object Coil {
        private const val version = "2.2.1"
        const val coilCompose = "io.coil-kt:coil-compose:$version"
    }

    object AirBnb {
        private const val version = "5.2.0"
        const val lottieCompose = "com.airbnb.android:lottie-compose:$version"
    }

    object Junit {
        private const val version = "4.13.2"
        const val testJunit = "junit:junit:$version"
    }

    object Truth {
        private const val version = "1.1.3"
        const val testTruth = "com.google.truth:truth:$version"
    }

    object Mockk {
        private const val version = "1.13.1"
        const val testMockk = "io.mockk:mockk:$version"
    }

    object AdeDom {
        private const val version = "0.3.2"
        const val myFoodKtorServer = "com.github.AdeDom:myfood-ktor-server:$version"
    }
}