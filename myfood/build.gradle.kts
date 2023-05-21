import com.adedom.buildsrc.Dependencies
import com.adedom.buildsrc.Flavors
import com.adedom.buildsrc.Versions

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.devtools.ksp)
    alias(libs.plugins.app.cash.paparazzi)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.insert.koin.androidx.compose)

    implementation(libs.jetbrains.kotlinx.coroutines.core)
    implementation(libs.jetbrains.kotlinx.coroutines.android)

    implementation(libs.androidx.datastore.preferences)

    implementation(libs.squareup.sqldelight.android.driver)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.myFood.ktor.server)

    testImplementation(libs.google.test.parameter.injector)
    testImplementation(libs.airbnb.android.showkase)
    kspTest(libs.airbnb.android.showkase.processor)
}