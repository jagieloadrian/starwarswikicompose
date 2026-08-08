@file:OptIn(ApolloExperimental::class)

import com.apollographql.apollo.annotations.ApolloExperimental
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.apollo.graphql)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.jacoco)
}

apollo {
    service("service") {
        packageName.set("com.anjo.starwarswikicompose.apollo")
        generateDataBuilders.set(true)
    }
}

val passwordPropertiesFile = rootProject.file("passwords.properties")
val passwordProperties = Properties()
passwordProperties.load(FileInputStream(passwordPropertiesFile))

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("release-keystore.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.anjo.starwarswikicompose"
    compileSdk = 37
    useLibrary("android.test.mock")
    buildFeatures.buildConfig = true

    defaultConfig {
        applicationId = "com.anjo.starwarswikicompose"
        minSdk = 34
        targetSdk = 36
        versionCode = 12
        versionName = "1.3"
        buildFeatures.buildConfig = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    androidComponents {
        onVariants { variant ->
            variant.outputs.forEach { output ->
                if (output is com.android.build.api.variant.impl.VariantOutputImpl) {
                    output.outputFileName = "${rootProject.name}-${variant.name}.apk"
                }
            }
        }
    }

    buildTypes {
        val flickrApiKey = "FLICKR_API"
        val author = "AUTHOR"
        val feedbackReceiver = "FEEDBACK_RECEIVER"
        val flickrApiKeyValue = (passwordProperties["flickrApiKey"] as String).trim('"')
        release {
            buildConfigField("String", flickrApiKey, "\"$flickrApiKeyValue\"")
            buildConfigField("String", author, "\"d18\"")
            buildConfigField("String", feedbackReceiver, "\"diether18.apps@gmail.com\"")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs["release"]
        }
        debug {
            buildConfigField("String", flickrApiKey, "\"$flickrApiKeyValue\"")
            buildConfigField("String", author, "\"d18\"")
            buildConfigField("String", feedbackReceiver, "\"diether18.apps@gmail.com\"")
            enableAndroidTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    testOptions {
        animationsDisabled = true
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/*"
        }
    }
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

dependencies {
    //apollo implementation
    implementation(libs.apollo.runtime)
    implementation(libs.apollo.api)
    implementation(libs.apollo.normalized.cache)
    implementation(libs.apollo.normalized.cache.sqlite)
    implementation(libs.androidx.junit.ktx)

    //Core and compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons)
    implementation(libs.kotlin.stdlib)

    // System UI Controller - Accompanist
    implementation(libs.androidx.paging.compose)
    debugImplementation(libs.androidx.ui.tooling)

    //junit5
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(platform(libs.junit.bom))
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.jupiter.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.vintage.engine)
    testImplementation(libs.kotlinx.coroutines.test)

    //androidTestLibraries
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.core.ktx.test)
    androidTestImplementation(libs.androidx.monitor)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //mockito during instrumented
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.agent)
    androidTestImplementation(libs.kotest.kotest.assertions.core)

    //mockk and kotest
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.kotest.assertions.core)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //kotlinx serialization
    implementation(libs.kotlinx.serialization.json)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    implementation(libs.dagger.producers)

    // Compose - navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    //Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttp)

    //DataStore
    implementation(libs.androidx.datastore.preferences)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    //Permissions
    implementation(libs.accompanist.permissions)

    //Play Ads
    implementation(libs.google.play.ads)
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    mustRunAfter("connectedDebugAndroidTest")
    val fileFilter = listOf("**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "android/**/*.*",
            //APOLLO files
            "**/com/anjo/starwarswikicompose/apollo/**",
            //Custom not able to test files
            "**/com/anjo/starwarswikicompose/ui/**",
            "**/com/anjo/starwarswikicompose/services/di/**",
            "**/com/anjo/starwarswikicompose/services/data/**/*Db.*",
            "**/com/anjo/starwarswikicompose/services/data/**/*Dao.*",
            // Hilt
            "**/*_HiltModules.class",
            "**/*_HiltComponents.class",
            "**/*_GeneratedInjector.class",
            "**/Dagger*.class",
            "**/*Module*Impl.class",
            "**/*Component*Impl.class",
            "**/Hilt_*.*",
            "**/*_Factory*.*",
            "**/*_Impl*.*"
    )
    val debugTree = fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") { exclude(fileFilter) }
    val javaClasses = fileTree("${layout.buildDirectory.get().asFile}/intermediates/javac/debug/classes") {
        exclude(fileFilter)
    }
    val mainSrc = files("src/main/java")

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree, javaClasses))
    executionData.setFrom(fileTree(layout.buildDirectory.get().asFile) {
        include(
                "jacoco/testDebugUnitTest.exec", // Unit tests
                "outputs/code_coverage/debugAndroidTest/connected/**/*.ec" // Android tests
        )
    })

    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}