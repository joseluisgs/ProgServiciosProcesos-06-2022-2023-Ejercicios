val ktor_version: String by project
val kotlin_version: String by project
val logbackclassic_version: String by project
val micrologging_version: String by project

val junit_version: String by project
val mockk_version: String by project
val coroutines_version: String by project

val koin_ktor_version: String by project
val ksp_version: String by project
val koin_ksp_version: String by project
val koin_version: String by project

val bcrypt_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // Ktor core
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")

    // Motor de kTOR
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")

    //Auth JWT
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")

    //JSON content
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // content validation
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")

    // Cors
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")

    // Websocket
    implementation("io.ktor:ktor-server-websockets-jvm:$ktor_version")

    // Certificados SSL y TSL
    implementation("io.ktor:ktor-network-tls-certificates:$ktor_version")

    // Logg
    implementation("ch.qos.logback:logback-classic:$logbackclassic_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$micrologging_version")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    implementation("io.ktor:ktor-client-auth:2.2.3")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")

    // BCrypt
    implementation("org.mindrot:jbcrypt:$bcrypt_version")

    // test
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // JUnit 5 en vez del por defecto de Kotlin...
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit_version")

    // MockK para testear Mockito con Kotlin
    testImplementation("io.mockk:mockk:$mockk_version")

    // Para testear métodos suspendidos o corrutinas
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")

    testImplementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
}
sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}