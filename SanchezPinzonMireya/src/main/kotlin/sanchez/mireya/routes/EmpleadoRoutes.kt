package sanchez.mireya.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import sanchez.mireya.models.Empleado
import sanchez.mireya.services.empleado.EmpleadosServiceImpl

private const val ENDPOINT = "api/empleados"

fun Application.empleadosRoutes() {
    val empleadoService: EmpleadosServiceImpl by inject()

    routing {
        route("/$ENDPOINT") {
            get {
                val result = mutableListOf<Empleado>()

                empleadoService.findAll().collect {
                    result.add(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            get("{id}") {
                try {
                    val id = call.parameters["id"]!!.toInt()
                    val empleado = empleadoService.findById(id)

                    call.respond(HttpStatusCode.OK, empleado.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post {
                try {
                    val empleadoReceive = call.receive<Empleado>()
                    val empleadoSave = empleadoService.save(empleadoReceive)
                    call.respond(HttpStatusCode.Created, empleadoService.findById(empleadoSave.id).toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()!!
                    val request = call.receive<Empleado>()
                    val empleado = empleadoService.update(id, request)
                    call.respond(HttpStatusCode.OK, empleado.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()!!

                    empleadoService.delete(id)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }

}