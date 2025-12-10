plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    application
}

group = "backend"
version = "0.0.1"

application {
    mainClass.set("backend.ApplicationKt")
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    val ktor_version = "3.0.2"

    // Ktor Server
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

    // Exposed
    implementation("org.jetbrains.exposed:exposed-core:0.44.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.44.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.44.1")

    // Database
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.zaxxer:HikariCP:5.0.1")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.11")

    testImplementation("io.ktor:ktor-server-test-host:${ktor_version}")
    testImplementation("io.ktor:ktor-client-mock:${ktor_version}")
    testImplementation(kotlin("test"))
}
tasks.test {
    enabled = false
}
