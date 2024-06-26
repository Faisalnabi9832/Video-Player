plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.folderpractice"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.folderpractice"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")


    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation ("com.squareup.picasso:picasso:2.71828")

    implementation ("com.google.android.exoplayer:exoplayer:2.14.1")
    implementation ("androidx.multidex:multidex:2.0.1")
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    implementation ("com.github.bullheadandplato:AndroidEqualizer:2.2")
//    implementation ("com.droidninja:filepicker:2.2.5")
//    implementation ("com.github.TutorialsAndroid:FilePicker:v8.0.19")

    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation ("com.github.bullheadandplato:AndroidEqualizer:2.2")





    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}