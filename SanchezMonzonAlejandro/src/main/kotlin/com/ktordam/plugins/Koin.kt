package com.ktordam.plugins

import io.ktor.server.application.*
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin

/**
 * Función que sirve para configurar Koin.
 */
fun Application.configureKoin() {
    install(Koin) {
        defaultModule()
    }
}