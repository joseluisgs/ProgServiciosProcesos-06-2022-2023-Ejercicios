plugins {
    application
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
    id("org.jetbrains.dokka") version "1.7.20"
}

group = "com.ktordam"
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
    // Ktor Core
    implementation("io.ktor:ktor-server-core-jvm:2.2.2")

    // JWT Authentication
    implementation("io.ktor:ktor-server-auth-jvm:2.2.2")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:2.2.2")

    // Server Content
    implementation("io.ktor:ktor-server-content-negotiation-jvm:2.2.2")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.2.2")

    // Json Serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.2.2")

    // Server
    implementation("io.ktor:ktor-server-host-common-jvm:2.2.2")
    implementation("io.ktor:ktor-server-netty-jvm:2.2.2")

    // Loggers
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.3")

    // Koin
    implementation("io.insert-koin:koin-ktor:3.3.0")
    implementation("io.insert-koin:koin-logger-slf4j:3.3.0")
    implementation("io.insert-koin:koin-annotations:1.1.0")
    ksp("io.insert-koin:koin-ksp-compiler:1.1.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // Caché
    implementation("io.github.reactivecircus.cache4k:cache4k:0.9.0")

    // BCrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")

    // Dokka
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")

    // Swagger
    implementation("io.github.smiley4:ktor-swagger-ui:1.0.2")

    testImplementation("io.ktor:ktor-server-tests-jvm:2.2.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.0")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    // Coroutine Tests
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Server Content Test
    testImplementation("io.ktor:ktor-client-content-negotiation:2.2.2")

    // Para testear con Tokens
    implementation("io.ktor:ktor-client-auth:2.2.2")

    // MockK
    testImplementation("io.mockk:mockk:1.13.2")
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

ktor {
    docker {
        localImageName.set("repaso-psp")
        imageTag.set("0.0.1-preview")
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        portMappings.set(
            listOf(
                io.ktor.plugin.features.DockerPortMapping(
                    6969,
                    6969,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
            )
        )
    }
}
