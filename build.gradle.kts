plugins {
    kotlin("jvm") version "2.2.0"
    application
}

group = "me.centralhardware"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("MainKt")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

val ktgbotapiVersion = "27.1.2"

dependencies {
    implementation("dev.inmo:tgbotapi:$ktgbotapiVersion")
    implementation("com.github.centralhardware:ktgbotapi-commons:$ktgbotapiVersion-1")
    implementation("io.github.mivek:metarParser-services:2.20.0")
    implementation("io.arrow-kt:arrow-core:2.1.2")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("io.github.crackthecodeabhi:kreds:0.9.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(24)
}
