import com.adedom.buildsrc.Dependencies
import com.adedom.buildsrc.Flavors
import com.adedom.buildsrc.Versions

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("app.cash.paparazzi") version "1.2.0"
}

android {
    namespace = "com.adedom.myfood"
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
}

dependencies {

    implementation(project(Dependencies.Project.core))
    implementation(project(Dependencies.Project.coreUiComponents))
    implementation(project(Dependencies.Project.dataProfile))
    implementation(project(Dependencies.Project.dataWebsockets))
    implementation(project(Dependencies.Project.dataFood))
    implementation(project(Dependencies.Project.dataFavorite))
    implementation(project(Dependencies.Project.domainUserProfile))
    implementation(project(Dependencies.Project.domainWebsockets))
    implementation(project(Dependencies.Project.featuresAuthentication))
    implementation(project(Dependencies.Project.featuresConnectivity))
    implementation(project(Dependencies.Project.featuresFoodDetail))
    implementation(project(Dependencies.Project.featuresMain))
    implementation(project(Dependencies.Project.featuresSearchFood))
    implementation(project(Dependencies.Project.featuresSplashScreen))
    implementation(project(Dependencies.Project.featuresUserProfile))
    implementation(project(Dependencies.Project.featuresWelcome))

    implementation(Dependencies.AndroidXCore.coreKtx)
    implementation(Dependencies.AndroidMaterial.material)

    implementation(Dependencies.Koin.koinAndroidxCompose)

    implementation(Dependencies.KotlinX.coroutinesCore)
    implementation(Dependencies.KotlinX.coroutinesAndroid)

    implementation(Dependencies.AndroidXDataStore.datastorePreferences)

    implementation(Dependencies.SquareUp.sqlDelightAndroidDriver)

    implementation(Dependencies.AndroidXNavigation.navigationCompose)

    implementation(Dependencies.AdeDom.myFoodKtorServer)

    testImplementation(Dependencies.TestParameterInjector.testParameterInjector)
    testImplementation(Dependencies.AirBnbShowkase.showkase)
    kspTest(Dependencies.AirBnbShowkase.showkaseProcessor)
}