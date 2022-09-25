import com.adedom.buildsrc.Dependencies
import com.adedom.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
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
    namespace = "com.adedom.profile"
}

dependencies {

    implementation(project(Dependencies.Project.core))

    implementation(Dependencies.AndroidXCore.coreKtx)

    implementation(Dependencies.KotlinX.coroutinesCore)
    implementation(Dependencies.KotlinX.coroutinesAndroid)

    implementation(Dependencies.Ktor.clientCore)
    implementation(Dependencies.Ktor.clientCio)
    implementation(Dependencies.Ktor.clientLogging)
    implementation(Dependencies.Ktor.clientContentNegotiation)
    implementation(Dependencies.Ktor.serializationKotlinxJson)
    implementation(Dependencies.Ktor.clientAuth)

    implementation(Dependencies.SquareUp.sqlDelightCoroutinesExt)

    implementation(Dependencies.AdeDom.myFoodKtorServer)

    testImplementation(Dependencies.Junit.testJunit)
    testImplementation(Dependencies.Truth.testTruth)
    testImplementation(Dependencies.KotlinX.testCoroutinesTest)
    testImplementation(Dependencies.Ktor.testClientMock)
    testImplementation(Dependencies.Mockk.testMockk)
}