package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureStorage()
    configureSerialization()
    configureRouting()
    configureValidation()
    configureSwagger()
}
