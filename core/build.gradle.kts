import com.adedom.buildsrc.Dependencies
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

    implementation(Dependencies.AndroidXCore.coreKtx)
    implementation(Dependencies.AndroidXActivity.activityCompose)
    implementation(Dependencies.AndroidGoogle.material)

    implementation(Dependencies.KotlinX.coroutinesCore)

    implementation(Dependencies.Ktor.clientCore)
    implementation(Dependencies.Ktor.clientCio)
    implementation(Dependencies.Ktor.clientLogging)
    implementation(Dependencies.Ktor.clientContentNegotiation)
    implementation(Dependencies.Ktor.serializationKotlinxJson)
    implementation(Dependencies.Ktor.clientAuth)

    implementation(Dependencies.AdeDom.myFoodKtorServer)
}