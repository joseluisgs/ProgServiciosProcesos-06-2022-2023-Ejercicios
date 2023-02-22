package es.ruymi

import io.ktor.server.application.*
import es.ruymi.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
