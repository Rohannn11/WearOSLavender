plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.lavender"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lavender"
        minSdk = 32
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.accessibility.test.framework)
    implementation(libs.play.services.location)
    implementation(libs.androidx.compiler)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation (libs.wear) // Add support for wearable specific inputs
    implementation (libs.wear.input)
    implementation (libs.wear.input.testing) // Use to implement wear ongoing activities
    implementation( libs.wear.ongoing) // Use to implement support for interactions from the Wearables to Phones
    implementation(libs.wear.phone.interactions) // Use to implement support for interactions between the Wearables and Phones
    implementation(libs.wear.remote.interactions)
    implementation(libs.compose.material.v140)
    implementation(libs.androidx.compose.foundation.v140)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.compose.ui.tooling)

    // Icons (if needed)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material3) // Use the latest version
    implementation(libs.androidx.compose.material3) // Add wear compose M3 [2]


    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.lifecycle.runtime.ktx)

    // 🟢 Navigation for Jetpack Compose
    implementation(libs.androidx.navigation.compose)

    // 🟢 Swipe Dismiss for Wear OS
    implementation(libs.androidx.compose.navigation)

    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.core)



}