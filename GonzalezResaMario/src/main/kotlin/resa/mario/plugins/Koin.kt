package resa.mario.plugins

import io.ktor.server.application.*
import org.koin.ksp.generated.defaultModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        // Logs de Koin
        slf4jLogger()
        // Modulo generado por defecto cuando se construye el proyecto (Funcionara cuando al menos se tenga una anotacion)
        defaultModule()
    }
}