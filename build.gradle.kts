plugins {
    kotlin("jvm") version "2.1.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
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

dependencies {
    implementation("dev.inmo:tgbotapi:25.0.1")
    implementation("com.github.centralhardware:ktgbotapi-commons:6ef1dde4fe")
    implementation("io.github.mivek:metarParser-services:2.18.0")
    implementation("io.arrow-kt:arrow-core:2.1.2")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.10.0")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("io.github.crackthecodeabhi:kreds:0.9.1")
    implementation("com.github.seratch:kotliquery:1.9.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
