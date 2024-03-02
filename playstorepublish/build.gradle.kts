plugins {
    id("kotlin")
    id("application")
}

group = "com.anjo"
version = "1.0"


application {
    application.mainClass.set("com.anjo.playstorepublish.Main")
}

dependencies {
    implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
    implementation("com.google.apis:google-api-services-androidpublisher:v3-rev20230921-2.0.0")
}

tasks.test {
    useJUnitPlatform()
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}