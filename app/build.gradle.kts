plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.kapt")  // REQUIRED FOR XML + HILT + ROOM
    alias(libs.plugins.hilt.android)
}


android {
    namespace = "com.example.wallpaperapp"
    compileSdk = 34  // use stable, not release(36)

    defaultConfig {
        applicationId = "com.example.wallpaperapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
        viewBinding = true
    }
}

dependencies {

    // -----------------------
    // AndroidX & UI
    // -----------------------
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // RecyclerView & CardView
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)

    // Material Components (for modern UI)
    implementation("com.google.android.material:material:1.12.0")

    // -----------------------
    // Lifecycle (ViewModel + LiveData)
    // -----------------------
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    // -----------------------
    // Retrofit + GSON
    // -----------------------
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // -----------------------
    // Coroutines
    // -----------------------
    implementation(libs.kotlinx.coroutines.android)

    // -----------------------
    // Coil (Image Loading)
    // -----------------------
    implementation(libs.coil)

    // -----------------------
    // Room (use KAPT — NOT KSP)
    // -----------------------
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // -----------------------
    // Hilt (Dependency Injection)
    // -----------------------
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // -----------------------
    // Navigation Component (recommended)
    // -----------------------
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // ❗ NO COMPOSE DEPENDENCIES
    // ❗ NO KSP (only KAPT)
}
