plugins {
    // Apply the Android application plugin
    alias(libs.plugins.android.application)
    // Apply Kotlin Android plugin for Kotlin support on Android
    alias(libs.plugins.kotlin.android)
    // Enable Kotlin Compose plugin for Jetpack Compose support
    alias(libs.plugins.kotlin.compose)
    // Kotlin annotation processing tool (kapt) for libraries like Room
    kotlin("kapt")
}

android {
    // Package namespace for your app
    namespace = "com.example.olyaandroid"
    // Android SDK version used to compile the app
    compileSdk = 35

    defaultConfig {
        // Application ID, uniquely identifies your app on the device and Play Store
        applicationId = "com.example.olyaandroid"
        // Minimum SDK version your app supports
        minSdk = 24
        // Target SDK version your app is optimized for
        targetSdk = 35
        // Version code for app updates (integer incremented each release)
        versionCode = 1
        // Version name visible to users
        versionName = "1.0"

        // Instrumentation test runner to run UI tests
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Disable code shrinking for release build (change to true for production)
            isMinifyEnabled = false
            // Proguard rules for optimizing and obfuscating release build
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        // Use Java 11 features for source and target compatibility
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        // Set JVM target version for Kotlin compiler to Java 11
        jvmTarget = "11"
    }

    buildFeatures {
        // Enable Jetpack Compose support in this module
        compose = true
    }
}

dependencies {
    // Core Android KTX extensions for more concise Kotlin code
    implementation(libs.androidx.core.ktx)
    // Lifecycle runtime for Android components (ViewModel, LiveData, etc.)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Compose integration with Activity
    implementation(libs.androidx.activity.compose)
    // Compose BOM (Bill of Materials) to manage consistent Compose versions
    implementation(platform(libs.androidx.compose.bom))
    // Core UI toolkit for Compose
    implementation(libs.androidx.ui)
    // Graphics support for Compose UI
    implementation(libs.androidx.ui.graphics)
    // Preview support in Compose tooling
    implementation(libs.androidx.ui.tooling.preview)
    // Material3 Design components for Compose
    implementation(libs.androidx.material3)
    // ViewModel integration for Compose (lifecycle-viewmodel-compose)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    // Lifecycle runtime for Compose integration
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    // Navigation library for Compose to handle screen navigation
    implementation("androidx.navigation:navigation-compose:2.9.0")

    // Unit testing framework JUnit
    testImplementation(libs.junit)
    // AndroidX JUnit extensions for instrumentation tests
    androidTestImplementation(libs.androidx.junit)
    // Espresso UI testing library
    androidTestImplementation(libs.androidx.espresso.core)
    // Compose BOM for Android tests to ensure version alignment
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // Compose UI test utilities with JUnit4 support
    androidTestImplementation(libs.androidx.ui.test.junit4)
    // Compose tooling debug helpers
    debugImplementation(libs.androidx.ui.tooling)
    // Manifest related debug support for UI tests
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room persistence library runtime for database support
    implementation("androidx.room:room-runtime:2.6.1")
    // Kotlin extensions for Room for easier coroutine support
    implementation("androidx.room:room-ktx:2.6.1")
    // Annotation processor for Room to generate database code
    kapt("androidx.room:room-compiler:2.6.1")
}
