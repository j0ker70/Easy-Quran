plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.dagger.hilt)
    kotlin("plugin.serialization") version "2.1.10"
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.network"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://api.quran.com/api/v4/\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.quran.com/api/v4/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {

    implementation(projects.core.model)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
}