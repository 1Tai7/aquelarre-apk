plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.crashlytics)

}

android {
    namespace = "firebase.app.aquelarre"
    compileSdk = 35

    defaultConfig {
        applicationId = "firebase.app.aquelarre"
        minSdk = 24
        targetSdk = 35
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    buildToolsVersion = "35.0.0"
}

dependencies {



    implementation(libs.androidx.emoji2.views.helper)
    implementation(libs.androidx.emoji2)
    implementation(libs.androidx.emoji2.bundled)
    implementation(libs.material)
    implementation(libs.material3)
    implementation(libs.androidx.material.icons.extended.v161)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.firestore)

    implementation(libs.firebase.crashlytics)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.emoji2.emojipicker)
    implementation(libs.androidx.espresso.core)


    debugImplementation(libs.ui.test.manifest)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    testImplementation(libs.junit.v4)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    testImplementation("androidx.compose.ui:ui-tooling:1.5.4")
    testImplementation("androidx.compose.ui:ui-test:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")

testImplementation(libs.kotlinx.coroutines.test)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}