package drodriguez.es.routes

import drodriguez.es.dto.EmpleadoDto
import drodriguez.es.mappers.toDto
import drodriguez.es.mappers.toEmpleado
import drodriguez.es.services.StorageService
import drodriguez.es.services.empleado.EmpleadoService
import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*


private const val END_POINT = "empleados"

fun Application.empleadosRoutes() {
    val empleadoService: EmpleadoService by inject()
    val storageService: StorageService by inject()

    routing {
        route("/$END_POINT") {
            get({
                description = "Find All Empleados"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de empleados"
                        body<List<EmpleadoDto>> { description = "Lista de empleados" }
                    }
                }
            }
            ) {
                val result = mutableListOf<EmpleadoDto>()
                empleadoService.findAll().collect {
                    result.add(it.toDto())
                }
                empleadoService.findAll().collect {
                    println(it)
                }
                call.respond(HttpStatusCode.OK, result)
            }

            get("{id}", {
                description = "Find by id empleado"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado a buscar:"
                        required = true
                    }
                }
            }
            ) {
                try {
                    val id = call.parameters["id"]!!
                    val empleado = empleadoService.findById(UUID.fromString(id))
                    call.respond(HttpStatusCode.OK, empleado.toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post({
                description = "New Empleados"
                request {
                    body<EmpleadoDto> {
                        description = "Save Empleado"
                    }
                }
            }
            ) {
                try {
                    val empleadoReceive = call.receive<EmpleadoDto>()
                    val empleadoSave = empleadoReceive.toEmpleado()
                    empleadoService.save(empleadoSave)
                    call.respond(HttpStatusCode.Created, empleadoService.findById(empleadoSave.id).toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}", {
                description = "Update empleado"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado"
                        required = true
                    }
                    body<EmpleadoDto> {
                        description = "Se recibe un empleado"
                    }
                }
            }) {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<EmpleadoDto>()
                    val empleado = empleadoService.update(UUID.fromString(id), request.toEmpleado())
                    call.respond(HttpStatusCode.OK, empleado.toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("/avatar/{id}", {
                description = "Actualizar empleado / AVATAR"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado"
                        required = true
                    }
                }

            }) {
                try {
                    val avatar = call.receiveChannel()
                    val id = call.parameters["id"]!!
                    val empleadoUpdate = empleadoService.findById(UUID.fromString(id))
                    val fileName = "${empleadoUpdate.id}.png"
                    storageService.saveFile(fileName, avatar)
                    empleadoUpdate.avatar = fileName
                    empleadoService.update(empleadoUpdate.id, empleadoUpdate)
                    call.respond(HttpStatusCode.OK, empleadoUpdate.toDto())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}", {
                description = "Delete id empleado"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado a eliminar"
                        required = true
                    }
                }
            }) {
                try {
                    val id = call.parameters["id"]!!
                    val empleadoDelete = empleadoService.findById(UUID.fromString(id))
                    empleadoService.delete(empleadoDelete)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }
}