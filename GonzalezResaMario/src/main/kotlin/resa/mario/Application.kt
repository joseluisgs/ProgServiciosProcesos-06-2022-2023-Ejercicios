package resa.mario

/**
 * @Author Mario Resa
 */

import io.ktor.server.application.*
import resa.mario.plugins.*

/**
 * Funcion principal del programa.
 *
 * @param args
 */
fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    // Koin
    configureKoin()

    // "Base de datos" = Mapas
    configureDataBase()

    // Almacenamiento
    configureStorage()

    // Default
    configureSerialization()
    configureSecurity()
    configureRouting()

    // Swagger
    configureSwagger()
}
