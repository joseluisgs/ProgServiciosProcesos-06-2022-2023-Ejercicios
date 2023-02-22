val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
    id("org.jetbrains.dokka") version "1.7.20"
    application
}

group = "drodriguez.es"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // Ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    // Ktor JWT
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-resources:$ktor_version")
    // Caching
    implementation("io.ktor:ktor-server-caching-headers-jvm:$ktor_version")
    // Compression
    implementation("io.ktor:ktor-server-compression-jvm:$ktor_version")
    // Negotation
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    // Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    // Web Sockets
    implementation("io.ktor:ktor-server-websockets-jvm:$ktor_version")
    // Netty Ktor
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    // Koin
    implementation("io.insert-koin:koin-ktor:3.3.0")
    implementation("io.insert-koin:koin-logger-slf4j:3.3.0")
    implementation("io.insert-koin:koin-annotations:1.1.0")
    ksp("io.insert-koin:koin-ksp-compiler:1.1.0")
    // Bcrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")
    // Ktor validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")
    implementation("io.github.smiley4:ktor-swagger-ui:1.0.2")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("ch.qos.logback:logback-classic:1.4.5")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
}
tasks.test {
    useJUnitPlatform()
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}