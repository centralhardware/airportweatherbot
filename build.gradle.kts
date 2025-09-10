plugins {
    kotlin("jvm") version "2.2.20"
    id("com.google.cloud.tools.jib") version "3.4.5"
}

group = "me.centralhardware"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

val ktgbotapiVersion = "28.0.0"

dependencies {
    implementation("dev.inmo:tgbotapi:$ktgbotapiVersion")
    implementation("com.github.centralhardware:ktgbotapi-commons:$ktgbotapiVersion")
    implementation("io.github.mivek:metarParser-services:2.20.0")
    implementation("io.arrow-kt:arrow-core:2.1.2")
    implementation("com.google.guava:guava:33.4.8-jre")
    implementation("io.github.crackthecodeabhi:kreds:0.9.1")
}

jib {
    from {
        image = System.getenv("JIB_FROM_IMAGE") ?: "eclipse-temurin:24-jre"
    }
    to {
    }
    container {
        mainClass = "MainKt"
        jvmFlags = listOf("-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0")
        creationTime = "USE_CURRENT_TIMESTAMP"
        labels = mapOf(
            "org.opencontainers.image.source" to (System.getenv("GITHUB_SERVER_URL")?.let { server ->
                val repo = System.getenv("GITHUB_REPOSITORY")
                if (repo != null) "$server/$repo" else ""
            } ?: ""),
            "org.opencontainers.image.revision" to (System.getenv("GITHUB_SHA") ?: "")
        )
        user = "10001"
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(24)
}
