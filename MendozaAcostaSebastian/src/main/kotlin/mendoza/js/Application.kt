package mendoza.js

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import mendoza.js.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    configureKoin()
    configureDataBase()
    configureSecurity()
    configureWebSockets()
    configureSerialization()
    configureRouting()
    configureCors()
    configureSwagger()
}
