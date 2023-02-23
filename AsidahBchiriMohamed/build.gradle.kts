val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project


val koin_ktor_version: String by project
val ksp_version: String by project
val koin_ksp_version: String by project
val koin_version: String by project

plugins {
    kotlin("jvm") version "1.8.0"
    id("io.ktor.plugin") version "2.2.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.0-1.0.8"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}
repositories {
    mavenCentral()
}

dependencies {
    //Ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-request-validation:$ktor_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")
    //Authentication
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    //koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_ktor_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")

    //Swagger
    implementation("io.ktor:ktor-server-swagger:$ktor_version")

    //Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    testImplementation("io.mockk:mockk:1.13.2")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.2.3")

    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}