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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }
    namespace = "com.adedom.food_detail"
}

dependencies {

    implementation("androidx.core:core-ktx:${Versions.coreKtxVersion}")
    implementation("androidx.activity:activity-compose:${Versions.activityComposeVersion}")
    implementation("androidx.compose.material:material:${Versions.composeVersion}")
    implementation("androidx.compose.ui:ui:${Versions.composeVersion}")
    implementation("androidx.compose.ui:ui-tooling-preview:${Versions.composeVersion}")
    implementation(project(":core"))
    implementation(project(":core:ui_components"))

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleVersion}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineVersion}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}")

    implementation("io.ktor:ktor-client-core:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-cio:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-logging:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-auth:${Versions.ktorVersion}")

    implementation("com.github.AdeDom:myfood-ktor-server:${Versions.myFoodVersion}")

    debugImplementation("androidx.compose.ui:ui-tooling:${Versions.composeVersion}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${Versions.composeVersion}")
}