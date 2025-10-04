// app/build.gradle.kts (The full updated file)

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android) // Hilt plugin
    kotlin("kapt")
}

android {
    namespace = "com.app.keshavassignment"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.keshavassignment"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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

    buildFeatures {
        // ⭐ Add or ensure this line is present and set to true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.recyclerview:recyclerview:1.3.1") // RecyclerView

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // HILT
    // Use the references from your libs.versions.toml for consistency
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // REMOVED: kapt("com.google.dagger:hilt-android-compiler:2.52") - was a duplicate

    // --- FIX: Room Database (Use the compatible 2.6.1 version) ---
    val room_version = "2.6.1"

    // Core Room library
    implementation("androidx.room:room-runtime:$room_version")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    // Annotation Processor (fixes the metadata error by using a newer, compatible processor)
    kapt("androidx.room:room-compiler:$room_version")

    // REMOVED: implementation(libs.androidx.room.runtime.jvm) - conflicted with the new version

    // Lifecycle KTX (for Flow.asLiveData())
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")

    // REMOVED: implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.9.0") - This is often
    // unnecessary if Room is updated, as Room bundles the compatible version.

    // Gson
    implementation("com.google.code.gson:gson:2.11.0")

    // SDP/SSP for scalable UI
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    // Javapoet (often a transitive dependency, but kept if you need it explicitly)
    implementation("com.squareup:javapoet:1.13.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Optional: for Room testing
    testImplementation("androidx.room:room-testing:$room_version")
}