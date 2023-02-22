package com.example.routes

import com.example.dto.EmpleadoDTO
import com.example.services.empleado.EmpleadoService
import com.example.services.storage.StorageService
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
import resa.mario.mappers.toDTO
import resa.mario.mappers.toEmpleado
import java.util.*

private const val END_POINT = "api/empleados"

fun Application.empleadosRoutes() {

    val empleadoService: EmpleadoService by inject()

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
                empleadoService.findAll().collect {
                    result.add(it.toDTO())
                }
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
                        description = "Id del empleado"
                        required = true
                    }
                }
                response {
                    HttpStatusCode.OK to {
                        description = "Id correcto"
                        body<EmpleadoDTO> { description = "EmpleadoDTO solicitado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Id incorrecto"
                        body<String> { description = "Exception" }
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
                        description = "EmpleadoDTO"
                    }
                }
                response {
                    HttpStatusCode.Created to {
                        description = "Empleado creado"
                        body<EmpleadoDTO> { description = "EmpleadoDTO creado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "Algun campo incorrecto"
                        body<String> { description = "Exception" }
                    }
                }
            }
            ) {
                try {
                    val empleadoReceive = call.receive<EmpleadoDTO>()
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
                        body<String> { description = "Exception" }
                    }
                }
            }) {
                try {
                    val id = call.parameters["id"]!!
                    val request = call.receive<EmpleadoDTO>()
                    val empleado = empleadoService.update( request.toEmpleado())
                    call.respond(HttpStatusCode.OK, empleado.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

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
                        description = "Archivo incorrecto"
                        body<String> { description = "Exception" }
                    }
                }
            }) {
                try {
                    val avatar = call.receiveChannel()
                    val id = call.parameters["id"]!!

                    val empleadoUpdate = empleadoService.findById(UUID.fromString(id))

                    val fileName = "${empleadoUpdate.id}.png"
                    storageService.saveFile(fileName, avatar)

                    // Actualizamos el avatar del usuario
                    empleadoUpdate.avatar = fileName
                    empleadoService.update( empleadoUpdate)

                    call.respond(HttpStatusCode.OK, empleadoUpdate.toDTO())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}", {
                description = "Delete by id empleado"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado"
                        required = true
                    }
                }

                response {
                    HttpStatusCode.NoContent to {
                        description = "Empleado eliminado"
                    }

                    HttpStatusCode.BadRequest to {
                        description = "Empleado no encontrado"
                        body<String> { description = "Exception" }
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