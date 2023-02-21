package resa.mario.routes

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
import resa.mario.dto.EmpleadoDTO
import resa.mario.mappers.toDTO
import resa.mario.mappers.toEmpleado
import resa.mario.services.StorageService
import resa.mario.services.empleado.EmpleadoServiceImpl
import java.util.*

private const val END_POINT = "api/empleados"

fun Application.empleadosRoutes() {

    val empleadoService: EmpleadoServiceImpl by inject()

    val storageService: StorageService by inject()

    routing {
        route("/$END_POINT") {
            // Get All
            get({
                description = "Get all empleados"
                response {
                    HttpStatusCode.OK to {
                        description = "Lista de empleadosDTO"
                        body<List<EmpleadoDTO>> { description = "Lista de empleadosDTO" }
                    }
                }
            }
            ) {
                val result = mutableListOf<EmpleadoDTO>()

                // Procesamos el flujo
                empleadoService.findAll().collect {
                    result.add(it.toDTO())
                }

                // Para poder ver los id
                empleadoService.findAll().collect {
                    println(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            // Get By Id
            get("{id}", {
                description = "Get by id empleado"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado que nos interese encontrar"
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Id correcto"
                        body<EmpleadoDTO> { description = "EmpleadoDTO solicitado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Id incorrecto, o no se encontro al empleado"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }
            ) {
                try {
                    val id = call.parameters["id"]!!
                    val empleado = empleadoService.findById(UUID.fromString(id))

                    call.respond(HttpStatusCode.OK, empleado.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Post /endpoint
            post({
                description = "Post empleado"
                request {
                    body<EmpleadoDTO> {
                        description = "Se recibe un EmpleadoDTO"
                    }
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Empleado creado correctamente"
                        body<EmpleadoDTO> { description = "EmpleadoDTO creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }
            ) {
                try {
                    // Recibimos al empleado
                    val empleadoReceive = call.receive<EmpleadoDTO>()

                    // DTO -> MODEL
                    val empleadoSave = empleadoReceive.toEmpleado()

                    empleadoService.save(empleadoSave)

                    call.respond(HttpStatusCode.Created, empleadoService.findById(empleadoSave.id).toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}", {
                description = "Put empleado"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado"
                        required = true
                    }
                    body<EmpleadoDTO> {
                        description = "Se recibe un EmpleadoDTO"
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Empleado actualizado"
                        body<EmpleadoDTO> { description = "EmpleadoDTO actualizado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto, empleado no localizado"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }) {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<EmpleadoDTO>()
                    val empleado = empleadoService.update(UUID.fromString(id), request.toEmpleado())
                    call.respond(HttpStatusCode.OK, empleado.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            // Actualizar solo el avatar
            put("/avatar/{id}", {
                description = "Put empleado / AVATAR"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado"
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Empleado avatar actualizado"
                        body<EmpleadoDTO> { description = "EmpleadoDTO actualizado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Archivo incorrecto, empleado no localizado"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
                    }
                }
            }) {
                try {
                    // Recibimos tanto el avatar como el id
                    val avatar = call.receiveChannel()
                    val id = call.parameters["id"]!!

                    // Obtenemos al empleado
                    val empleadoUpdate = empleadoService.findById(UUID.fromString(id))

                    // Damos nombre al archivo y lo almacenamos
                    val fileName = "${empleadoUpdate.id}.png"
                    storageService.saveFile(fileName, avatar)

                    // Actualizamos el avatar del usuario
                    empleadoUpdate.avatar = fileName
                    empleadoService.update(empleadoUpdate.id, empleadoUpdate)

                    call.respond(HttpStatusCode.OK, empleadoUpdate.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}", {
                description = "Delete by id empleado"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado a eliminar"
                        required = true
                    }
                }

                response {
                    HttpStatusCode.NoContent to {
                        description = "Empleado eliminado con exito"
                    }

                    HttpStatusCode.BadRequest to {
                        description = "Empleado no encontrado"
                        body<String> { description = "Mensaje con la excepcion correspondiente" }
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