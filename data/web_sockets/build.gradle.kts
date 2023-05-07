import com.adedom.buildsrc.Dependencies
import com.adedom.buildsrc.Flavors
import com.adedom.buildsrc.Versions

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlinx-serialization")
}

android {
    namespace = "com.adedom.data.web_sockets"
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
}

dependencies {

    implementation(project(Dependencies.Project.core))

    implementation(libs.jetbrains.kotlinx.coroutines.core)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.myFood.ktor.server)
}