import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.centralhardware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("dev.inmo:tgbotapi:23.1.0")
    implementation("com.github.centralhardware:telegram-bot-commons:34c054a4a1")
    implementation("io.github.mivek:metarParser-services:2.16.3")
    implementation("io.arrow-kt:arrow-core:2.0.0")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.10.0")
    implementation("com.google.guava:guava:33.4.0-jre")
    implementation("io.github.crackthecodeabhi:kreds:0.9.1")
    implementation("com.github.seratch:kotliquery:1.9.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "MainKt"))
        }
    }
}