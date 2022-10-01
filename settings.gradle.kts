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
include(":features:splash_screen")
include(":features:welcome")
include(":features:authentication")
include(":features:main")
include(":features:food_detail")
include(":features:connectivity")
include(":features:search_food")
include(":features:user_profile")
include(":myfood")
include(":data:profile")
include(":domain:user_profile")
