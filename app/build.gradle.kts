plugins {
    id("com.android.application")
    kotlin("android") version "1.8.0"
    kotlin("kapt") version "1.8.0"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.challengeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.challengeapp"
        minSdk = 26
        targetSdk = 34
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
    buildFeatures{
        viewBinding = true
    }
}

kapt {
    correctErrorTypes  = true
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("androidx.navigation:navigation-ui:2.7.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Test
    testImplementation ("org.junit.jupiter:junit-jupiter-api:5.8.0")
    testRuntimeOnly ("org.junit.jupiter:junit-jupiter-engine:5.8.0")
    testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("io.mockk:mockk:1.12.2")
    testImplementation ("org.mockito:mockito-core:5.2.0")
    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")
    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    // Activity
    implementation("androidx.activity:activity-ktx:1.8.2")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    //DI
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")


    //test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("junit:junit:")
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation("com.google.dagger:hilt-android-testing:2.38.1")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.44")

    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test:rules:1.5.0")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.airbnb.android:lottie:4.2.2")


    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.13.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    //Hamcrest
    testImplementation("org.hamcrest:hamcrest:2.2")

    //Room
    implementation("androidx.room:room-runtime:2.4.3")
    annotationProcessor("androidx.room:room-compiler:2.4.3")
    kapt("androidx.room:room-compiler:2.4.3")
    implementation("androidx.room:room-ktx:2.4.3")

    //SplashScreen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //Corrutinas test
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

}