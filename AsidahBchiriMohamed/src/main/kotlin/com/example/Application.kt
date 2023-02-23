package com.example

import com.example.controllers.EmpleadoController
import com.example.models.Empleado
import com.example.models.TypeRole
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.repositories.empleados.EmpleadoRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import java.util.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() = runBlocking {
    val controller = EmpleadoController(EmpleadoRepository())
    val admin = Empleado(
        name = "Moha",
        surname = "Asidah",
        email = "moha@mail.com",
        salary = 2000.00,
        departamento = "IT",
        rol = TypeRole.ADMIN
    )

    val despedido = Empleado(
        uuid = UUID.fromString("52981ced-0391-4591-9c60-d90d94b89d51"),
        name = "Manuel",
        surname = "Parada",
        email = "manuel@mail.com",
        salary = 2000.00,
        departamento = "Sales",
        rol = TypeRole.EMPLEADO
    )
    val ana = Empleado(
        uuid = UUID.fromString("2ab8f2d1-44de-4ea9-bfc3-718b2cb05cd6"),
        name = "Ana",
        surname = "Garcia",
        email = "ana@mail.com",
        salary = 2000.00,
        departamento = "RRHH"

    )
    controller.save(admin)
    controller.save(despedido)
    controller.save(ana)

    configureKoin()
    configureSerialization()
    configureStorage()
    configureSecurity()
    configureValidation()
    configureRouting()


}


class KoinApp : KoinComponent {
    fun runApp() = runBlocking {
        println("Hola")
        val controller = EmpleadoController(EmpleadoRepository())
        val empleado1 = Empleado(
            name = "Moha",
            surname = "Asidah",
            email = "correo",
            salary = 2000.00,
            departamento = "IT"
        )

        launch {
            controller.save(empleado1)
            println("AAAA")
        }
        embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
    }


}
