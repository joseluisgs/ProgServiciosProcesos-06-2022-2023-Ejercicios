package drodriguez.es

import io.ktor.server.application.*
import drodriguez.es.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureDataBase()
    configureSerialization()
    configureSecurity()
    configureRouting()

}
