import com.adedom.buildsrc.Dependencies
import com.adedom.buildsrc.Flavors
import com.adedom.buildsrc.Versions

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

android {
    namespace = "com.adedom.showkase"
    compileSdk = Versions.targetAndCompileVersion

    defaultConfig {
        applicationId = "com.adedom.showkase"
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetAndCompileVersion
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    packagingOptions {
        resources {
            excludes += "META-INF/io.netty.versions.properties"
            excludes += "META-INF/INDEX.LIST"
        }
    }
}

dependencies {

    implementation(project(Dependencies.Project.coreUiComponents))
    implementation(project(Dependencies.Project.featuresAuthentication))
    implementation(project(Dependencies.Project.featuresConnectivity))
    implementation(project(Dependencies.Project.featuresFoodDetail))
    implementation(project(Dependencies.Project.featuresMain))
    implementation(project(Dependencies.Project.featuresSearchFood))
    implementation(project(Dependencies.Project.featuresSplashScreen))
    implementation(project(Dependencies.Project.featuresUserProfile))
    implementation(project(Dependencies.Project.featuresWelcome))

    implementation(Dependencies.AndroidXCore.coreKtx)
    implementation(Dependencies.AndroidXActivity.activityCompose)

    implementation(Dependencies.AirBnbShowkase.showkase)
    ksp(Dependencies.AirBnbShowkase.showkaseProcessor)
}