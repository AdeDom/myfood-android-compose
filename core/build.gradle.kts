import com.adedom.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("MyFoodDatabase") {
        packageName = "com.adedom.myfood"
        schemaOutputDirectory = file("build/dbs")
    }
    linkSqlite = false
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
    namespace = "com.adedom.core"
}

dependencies {

    implementation("androidx.core:core-ktx:${Versions.coreKtxVersion}")
    implementation("androidx.activity:activity-compose:${Versions.activityComposeVersion}")
    implementation("com.google.android.material:material:${Versions.materialVersion}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineVersion}")

    implementation("io.ktor:ktor-client-core:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-cio:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-logging:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-content-negotiation:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-serialization-kotlinx-json:${Versions.ktorVersion}")
    implementation("io.ktor:ktor-client-auth:${Versions.ktorVersion}")

    implementation("com.github.AdeDom:myfood-ktor-server:${Versions.myFoodVersion}")
}