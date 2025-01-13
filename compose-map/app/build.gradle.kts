import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.kotlin.ksp)
    alias(libs.plugins.android.hilt)
}

val properties = gradleLocalProperties(rootDir, providers)
val googleApiKey: String = properties.getProperty("google.api.key")
val naverClientId: String = properties.getProperty("naver.client.id")
val kakaoNativeAppKey: String = properties.getProperty("kakao.native.app.key")

android {
    namespace = "com.moondroid.composemap"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.moondroid.composemap"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders["googleApiKey"] = googleApiKey
        manifestPlaceholders["naverClientId"] = naverClientId
        manifestPlaceholders["kakaoNativeAppKey"] = kakaoNativeAppKey

        resValue("string", "naver_client_id", naverClientId)
        resValue("string", "kakao_native_app_key", kakaoNativeAppKey)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.navigation.compose)
    implementation(libs.kotlin.serialization)

    implementation(libs.google.map)
    implementation(libs.google.location)

    implementation(libs.naver.map)
    implementation(libs.naver.location)

    implementation(libs.kakao.map)

    // Hilt dependency injection
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.compiler)
}