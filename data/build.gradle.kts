plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.android.hilt)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.begin_a_gain.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 29
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    ksp(libs.di.google.hilt.compiler)
    implementation(libs.bundles.di.hilt)

    implementation(libs.bundles.data.ktor)
    implementation(libs.bundles.data.local)
    implementation(libs.kotlinx.serialization.json)
}