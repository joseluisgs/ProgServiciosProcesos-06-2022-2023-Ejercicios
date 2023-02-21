// Ktor y Kotlin
val ktor_version: String by project
val kotlin_version: String by project

// Logger
// val logback_version: String by project
val micrologging_version: String by project
val logbackclassic_version: String by project

// Koin
val koin_ktor_version: String by project
val ksp_version: String by project
val koin_ksp_version: String by project
val koin_version: String by project

// Test
val junit_version: String by project
val mockk_version: String by project
val coroutines_version: String by project

// BCrypt
val bcrypt_version: String by project

// Ktow Swagger UI
val ktor_swagger_ui_version: String by project

val cache_version: String by project


plugins {
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
    id("org.jetbrains.dokka") version "1.7.20"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    // Content validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logbackclassic_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    // Auth JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    // Certificados SSL y TSL
    implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")
    // Logging
    implementation("ch.qos.logback:logback-classic:$logbackclassic_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")
    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")
    // BCrypt
    implementation("org.mindrot:jbcrypt:$bcrypt_version")
    // Swagger
    implementation("io.github.smiley4:ktor-swagger-ui:$ktor_swagger_ui_version")
    // Cache 4K
    implementation("io.github.reactivecircus.cache4k:cache4k:$cache_version")
    // Para testear
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version") // Usar deps de JUNIT 5 y no estas!

    // JUnit 5 en vez del por defecto de Kotlin...
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit_version")

    // MockK para testear Mockito con Kotlin
    testImplementation("io.mockk:mockk:$mockk_version")

    // Para testear métodos suspendidos o corrutinas
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")

    // Para testear con content negotiation
    testImplementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

}

tasks.test {
    useJUnitPlatform()
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

// Para docker
ktor {
    docker {
        localImageName.set("departamento-empleado-ktor")
        imageTag.set("0.0.1-preview")
        jreVersion.set(io.ktor.plugin.features.JreVersion.JRE_17)
        portMappings.set(
            listOf(
                io.ktor.plugin.features.DockerPortMapping(
                    6969,
                    6969,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                ),
                io.ktor.plugin.features.DockerPortMapping(
                    6963,
                    6963,
                    io.ktor.plugin.features.DockerPortMappingProtocol.TCP
                )
            )
        )
    }
}