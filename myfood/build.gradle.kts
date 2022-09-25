import com.adedom.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
}

android {
    compileSdk = Versions.targetAndCompileVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetAndCompileVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions += "appType"
    productFlavors {
        create("develop") {
            dimension = "appType"
            buildConfigField("Boolean", "IS_DEVELOP_MODE", "true")
            buildConfigField("String", "BASE_URL", "\"https://myfood-server.herokuapp.com/\"")
            buildConfigField("String", "HOST", "\"myfood-server.herokuapp.com\"")
        }

        create("production") {
            dimension = "appType"
            buildConfigField("Boolean", "IS_DEVELOP_MODE", "false")
            buildConfigField("String", "BASE_URL", "\"https://myfood-server.herokuapp.com/\"")
            buildConfigField("String", "HOST", "\"myfood-server.herokuapp.com\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions.unitTests {
        isIncludeAndroidResources = true
        isReturnDefaultValues = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }
    namespace = "com.adedom.myfood"
}

dependencies {

    implementation("androidx.core:core-ktx:${Versions.coreKtxVersion}")
    implementation(project(":core"))
    implementation(project(":core:ui_components"))
    implementation(project(":features:splash_screen"))
    implementation(project(":features:welcome"))
    implementation(project(":features:main"))
    implementation(project(":features:authentication"))
    implementation(project(":features:connectivity"))
    implementation(project(":features:food_detail"))
    implementation(project(":features:search_food"))
    implementation(project(":features:user_profile"))
    implementation(project(":data:profile"))

    implementation("io.insert-koin:koin-core:${Versions.koinVersion}")
    implementation("io.insert-koin:koin-android:${Versions.koinVersion}")
    implementation("io.insert-koin:koin-androidx-compose:${Versions.koinVersion}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}")

    implementation("androidx.datastore:datastore-preferences:${Versions.datastorePreferencesVersion}")

    implementation("com.squareup.sqldelight:android-driver:${Versions.sqlDelightVersion}")

    implementation("androidx.navigation:navigation-compose:${Versions.navigationComposeVersion}")

    implementation("com.github.AdeDom:myfood-ktor-server:${Versions.myFoodVersion}")
}