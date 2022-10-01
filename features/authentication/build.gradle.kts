import com.adedom.buildsrc.Dependencies
import com.adedom.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
}

android {
    namespace = "com.adedom.authentication"
    compileSdk = Versions.targetAndCompileVersion

    defaultConfig {
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetAndCompileVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
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
    packagingOptions {
        resources {
            excludes += "META-INF/io.netty.versions.properties"
            excludes += "META-INF/INDEX.LIST"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(project(Dependencies.Project.core))
    implementation(project(Dependencies.Project.coreUiComponents))
    implementation(project(Dependencies.Project.dataProfile))

    implementation(Dependencies.AndroidXCore.coreKtx)
    implementation(Dependencies.AndroidXActivity.activityCompose)
    implementation(Dependencies.AndroidXCompose.material)
    implementation(Dependencies.AndroidXCompose.ui)
    implementation(Dependencies.AndroidXCompose.uiToolingPreview)

    implementation(Dependencies.Lifecycle.viewModelKtx)
    implementation(Dependencies.Lifecycle.viewModelCompose)
    implementation(Dependencies.Lifecycle.livedataKtx)
    implementation(Dependencies.Lifecycle.runtimeKtx)

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
    testImplementation(Dependencies.AndroidXArch.testCoreTesting)

    androidTestImplementation(Dependencies.AndroidXCompose.androidTestUiTestJunit4)
    androidTestImplementation(Dependencies.Mockk.androidTestMockk)

    debugImplementation(Dependencies.AndroidXCompose.debugUiTooling)
    debugImplementation(Dependencies.AndroidXCompose.debugUiTestManifest)
}