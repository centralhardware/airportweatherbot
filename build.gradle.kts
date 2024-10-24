import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.21"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.centralhardware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("dev.inmo:tgbotapi:18.2.2")
    implementation("com.github.centralhardware:telegram-bot-commons:1b5b37063e")
    implementation("io.github.mivek:metarParser-services:2.16.0")
    implementation("io.arrow-kt:arrow-core:1.2.4")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.10.0")
    implementation("com.google.guava:guava:33.3.1-jre")
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