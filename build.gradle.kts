plugins {
    kotlin("jvm") version "2.0.0"
}

group = "me.centralhardware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("com.github.centralhardware:telegram-bot-commons:2a55dd22e2")
    implementation("dev.inmo:tgbotapi:13.0.0")
    implementation("io.github.mivek:metarParser-services:2.16.0")
    implementation("io.arrow-kt:arrow-core:1.2.4")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.3")
    implementation("com.google.guava:guava:33.2.0-jre")
    implementation("org.slf4j:slf4j-api:2.0.12")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("ch.qos.logback:logback-core:1.5.6")
    implementation("io.github.crackthecodeabhi:kreds:0.9.1")
    implementation("com.github.seratch:kotliquery:1.9.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
        archiveClassifier.set("standalone") // Naming the jar
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to "MainKt")) } // Provided we set it up in the application plugin configuration
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get()
            .map { if (it.isDirectory) it else zipTree(it) } +
                sourcesMain.output
        from(contents)
    }
    build {
        dependsOn(fatJar) // Trigger fat jar creation during build
    }
}