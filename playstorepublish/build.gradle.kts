plugins {
    id("kotlin")
    id("application")
    id("org.jetbrains.kotlin.jvm")
}

group = "com.anjo"
version = "1.0"


application {
    application.mainClass.set("com.anjo.playstorepublish.Main")
}

dependencies {
    implementation(libs.google.auth.library.oauth2.http)
    implementation(libs.google.api.services.androidpublisher)
}

tasks.test {
    useJUnitPlatform()
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}