package com.adedom.buildsrc

object Versions {
    const val minSdkVersion = 24
    const val targetAndCompileVersion = 33
    const val versionCode = 1
    const val versionName = "1.0"
    const val composeCompilerVersion = "1.4.3"
}

object Flavors {
    const val flavorDimensions = "appType"
    const val developDimension = "develop"
    const val productionDimension = "production"
    const val booleanTypeField = "boolean"
    const val stringTypeField = "String"
    const val isDevelopModeNameField = "IS_DEVELOP_MODE"
    const val baseUrlNameField = "BASE_URL"
    const val hostNameField = "HOST"

    object DevelopValueField {
        const val isDevelopMode = "true"
        const val baseUrl = "\"https://myfood-server.herokuapp.com/\""
        const val host = "\"myfood-server.herokuapp.com\""
    }

    object ProductionValueField {
        const val isDevelopMode = "false"
        const val baseUrl = "\"https://myfood-server.herokuapp.com/\""
        const val host = "\"myfood-server.herokuapp.com\""
    }
}

object Dependencies {

    object Project {
        const val app = ":app"
        const val core = ":core"
        const val coreUiComponents = ":core:ui_components"
        const val dataProfile = ":data:profile"
        const val dataWebsockets = ":data:web_sockets"
        const val dataFood = ":data:food"
        const val dataFavorite = ":data:favorite"
        const val domainUserProfile = ":domain:user_profile"
        const val domainWebsockets = ":domain:web_sockets"
        const val featuresAuthentication = ":features:authentication"
        const val featuresConnectivity = ":features:connectivity"
        const val featuresFoodDetail = ":features:food_detail"
        const val featuresMain = ":features:main"
        const val featuresSearchFood = ":features:search_food"
        const val featuresSplashScreen = ":features:splash_screen"
        const val featuresUserProfile = ":features:user_profile"
        const val featuresWelcome = ":features:welcome"
        const val myFood = ":myfood"
    }

    object AndroidXCore {
        private const val version = "1.9.0"
        const val coreKtx = "androidx.core:core-ktx:$version"
    }

    object AndroidXActivity {
        private const val version = "1.6.1"
        const val activityCompose = "androidx.activity:activity-compose:$version"
    }

    object AndroidXCompose {
        private const val version = "1.3.3"
        const val ui = "androidx.compose.ui:ui:$version"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val androidTestUiTestJunit4 = "androidx.compose.ui:ui-test-junit4:$version"
        const val debugUiTooling = "androidx.compose.ui:ui-tooling:$version"
        const val debugUiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
    }

    object AndroidMaterial {
        private const val version = "1.3.1"
        const val material = "androidx.compose.material:material:$version"
    }

    object AndroidXNavigation {
        private const val version = "2.5.3"
        const val navigationCompose = "androidx.navigation:navigation-compose:$version"
    }

    object AndroidXDataStore {
        private const val version = "1.0.0"
        const val datastorePreferences = "androidx.datastore:datastore-preferences:$version"
    }

    object AndroidXArch {
        private const val version = "2.2.0"
        const val testCoreTesting = "androidx.arch.core:core-testing:$version"
    }

    object Accompanist {
        private const val version = "0.28.0"
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
        private const val version = "2.2.4"
        const val clientCore = "io.ktor:ktor-client-core:$version"
        const val clientCio = "io.ktor:ktor-client-cio:$version"
        const val clientWebSockets = "io.ktor:ktor-client-websockets:$version"
        const val clientLogging = "io.ktor:ktor-client-logging:$version"
        const val clientContentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val serializationKotlinxJson = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val clientAuth = "io.ktor:ktor-client-auth:$version"
        const val testClientMock = "io.ktor:ktor-client-mock:$version"
    }

    object Koin {
        private const val version = "3.4.2"
        const val koinAndroidxCompose = "io.insert-koin:koin-androidx-compose:$version"
    }

    object SquareUp {
        private const val version = "1.5.5"
        const val sqlDelightCoroutinesExt = "com.squareup.sqldelight:coroutines-extensions:$version"
        const val sqlDelightAndroidDriver = "com.squareup.sqldelight:android-driver:$version"
    }

    object Coil {
        private const val version = "2.2.2"
        const val coilCompose = "io.coil-kt:coil-compose:$version"
    }

    object AirBnbLottie {
        private const val version = "6.0.0"
        const val lottieCompose = "com.airbnb.android:lottie-compose:$version"
    }

    object AirBnbShowkase {
        private const val version = "1.0.0-beta17"
        const val showkase = "com.airbnb.android:showkase:$version"
        const val showkaseProcessor = "com.airbnb.android:showkase-processor:$version"
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
        private const val version = "1.13.4"
        const val testMockk = "io.mockk:mockk:$version"
        const val androidTestMockk = "io.mockk:mockk-android:$version"
    }

    object AdeDom {
        private const val version = "0.3.2"
        const val myFoodKtorServer = "com.github.AdeDom:myfood-ktor-server:$version"
    }
}