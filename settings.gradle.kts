pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
    }
}
rootProject.name = "My Food"
include(":app")
include(":core")
include(":core:ui_components")
include(":data:profile")
include(":data:web_sockets")
include(":domain:user_profile")
include(":domain:web_sockets")
include(":features:authentication")
include(":features:connectivity")
include(":features:food_detail")
include(":features:main")
include(":features:search_food")
include(":features:splash_screen")
include(":features:user_profile")
include(":features:welcome")
include(":myfood")
