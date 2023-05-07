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
        const val baseUrl = "\"http://192.168.189.135:8080/\""
        const val host = "\"192.168.189.135:8080\""
    }

    object ProductionValueField {
        const val isDevelopMode = "false"
        const val baseUrl = "\"https://myfood-server.herokuapp.com/\""
        const val host = "\"myfood-server.herokuapp.com\""
    }
}

object Dependencies {

    object Project {
        const val showkase = ":showkase"
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
}