import com.adedom.buildsrc.Dependencies
import com.adedom.buildsrc.Flavors
import com.adedom.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
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

    flavorDimensions += Flavors.flavorDimensions
    productFlavors {
        create(Flavors.developDimension) {
            dimension = Flavors.flavorDimensions
            buildConfigField(
                Flavors.booleanTypeField,
                Flavors.isDevelopModeNameField,
                Flavors.DevelopValueField.isDevelopMode,
            )
            buildConfigField(
                Flavors.stringTypeField,
                Flavors.baseUrlNameField,
                Flavors.DevelopValueField.baseUrl,
            )
            buildConfigField(
                Flavors.stringTypeField,
                Flavors.hostNameField,
                Flavors.DevelopValueField.host,
            )
        }

        create(Flavors.productionDimension) {
            dimension = Flavors.flavorDimensions
            buildConfigField(
                Flavors.booleanTypeField,
                Flavors.isDevelopModeNameField,
                Flavors.ProductionValueField.isDevelopMode,
            )
            buildConfigField(
                Flavors.stringTypeField,
                Flavors.baseUrlNameField,
                Flavors.ProductionValueField.baseUrl,
            )
            buildConfigField(
                Flavors.stringTypeField,
                Flavors.hostNameField,
                Flavors.ProductionValueField.host,
            )
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
    implementation(project(Dependencies.Project.dataFavorite))
    implementation(project(Dependencies.Project.domainUserProfile))

    implementation(Dependencies.AndroidXCore.coreKtx)
    implementation(Dependencies.AndroidXActivity.activityCompose)
    implementation(Dependencies.AndroidMaterial.material)
    implementation(Dependencies.AndroidXCompose.ui)
    implementation(Dependencies.AndroidXCompose.uiToolingPreview)

    implementation(Dependencies.Lifecycle.viewModelKtx)
    implementation(Dependencies.Lifecycle.viewModelCompose)
    implementation(Dependencies.Lifecycle.livedataKtx)
    implementation(Dependencies.Lifecycle.runtimeKtx)

    implementation(Dependencies.KotlinX.coroutinesCore)
    implementation(Dependencies.KotlinX.coroutinesAndroid)

    implementation(Dependencies.Ktor.clientCore)

    implementation(Dependencies.AdeDom.myFoodKtorServer)

    implementation(Dependencies.AirBnbShowkase.showkase)
    ksp(Dependencies.AirBnbShowkase.showkaseProcessor)

    testImplementation(Dependencies.Junit.testJunit)
    testImplementation(Dependencies.Truth.testTruth)
    testImplementation(Dependencies.KotlinX.testCoroutinesTest)
    testImplementation(Dependencies.Ktor.testClientMock)
    testImplementation(Dependencies.Ktor.serializationKotlinxJson)
    testImplementation(Dependencies.Mockk.testMockk)
    testImplementation(Dependencies.AndroidXArch.testCoreTesting)

    androidTestImplementation(Dependencies.AndroidXCompose.androidTestUiTestJunit4)
    androidTestImplementation(Dependencies.Mockk.androidTestMockk)

    debugImplementation(Dependencies.AndroidXCompose.debugUiTooling)
    debugImplementation(Dependencies.AndroidXCompose.debugUiTestManifest)
}