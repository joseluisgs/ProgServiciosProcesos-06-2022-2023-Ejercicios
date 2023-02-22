package sanchez.mireya

import io.ktor.server.application.*
import sanchez.mireya.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureKoin()
    configureStorage()
    configureSecurity()
    configureSerialization()
    configureRouting()
    configureSwagger()
}
