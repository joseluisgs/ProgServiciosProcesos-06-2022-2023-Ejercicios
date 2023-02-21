package mendoza.js.routes

import io.github.smiley4.ktorswaggerui.dsl.delete
import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.post
import io.github.smiley4.ktorswaggerui.dsl.put
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mendoza.js.utils.UUIDException
import mendoza.js.utils.toUUID
import kotlinx.coroutines.flow.toList
import mendoza.js.dto.EmpleadoCreateDto
import mendoza.js.dto.EmpleadoDto
import mendoza.js.exceptions.EmpleadoBadRequestException
import mendoza.js.exceptions.EmpleadoNotFoundException
import mendoza.js.mappers.toDTO
import mendoza.js.mappers.toModel
import mendoza.js.service.empleado.EmpleadoService
import mu.KotlinLogging
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger { }

private const val ENDPOINT = "api/empleados"

fun Application.empleadosRoutes() {
    val empleadoService: EmpleadoService by inject()

    routing {
        route("/$ENDPOINT") {
            get({
                description = "Ger All empleados: Lista de empleados existente"
                response {
                    default {
                        description = "Lista de empleados"
                    }
                    HttpStatusCode.OK to {
                        description = "Lista de empleados"
                        body<EmpleadoDto> { description = "Lista de empleados" }
                    }
                }
            }) {
                logger.debug { "GET ALL /$ENDPOINT" }
                val res = empleadoService.findAll().toList()
                    .map { it.toDTO() }
                    .let { res -> call.respond(HttpStatusCode.OK, res) }
            }

            get("{id}", {
                description = "Get By Id: Buscar empleado por Id"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado"
                        required = true
                    }
                }
                response {
                    default {
                        description = "Empleado por Id"
                    }
                    HttpStatusCode.OK to {
                        description = "Emplead con Id indicado"
                        body<EmpleadoDto> { description = "Departamento encontrado" }
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el empleado"
                        body<String> { description = "No existe el empleado con ese id" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha indicado el id del empleado"
                        body<String> { description = "No se ha indicado el id del empleado a buscar" }
                    }
                }
            }) {
                logger.debug { "GET BY ID /$ENDPOINT/{id}" }
                try {
                    val id = call.parameters["id"]?.toUUID()!!
                    val emp = empleadoService.findById(id)
                    call.respond(
                        HttpStatusCode.OK, emp!!.toDTO()
                    )
                } catch (e: EmpleadoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: UUIDException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post({
                description = "Post empleado: Añadir un empleado nuevo"
                request {
                    body<EmpleadoCreateDto> {
                        description = "Empleado a agregar"
                    }
                }
                response {
                    default {
                        description = "Empleado a agregar"
                    }
                    HttpStatusCode.Created to {
                        description = "Empleado añadido"
                        body<EmpleadoDto> { description = "Nuevo empleado añadido" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha podido agregar el nuevo empleado"
                        body<String> { description = "Error al agregar el nuevo empleado" }
                    }
                }
            }) {
                logger.debug { "POST /$ENDPOINT" }
                try {
                    val dto = call.receive<EmpleadoCreateDto>()
                    val emp = empleadoService.save(dto.toModel())
                    call.respond(
                        HttpStatusCode.Created, emp.toDTO()
                    )
                } catch (e: EmpleadoBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}", {
                description = "Put By Id: Actualización de empleado po id"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado a actualizar"
                        required = true
                    }
                    body<EmpleadoDto> {
                        description = "Empleado a actualizar"
                    }
                }
                response {
                    default {
                        description = "Empleado a actualizado"
                    }
                    HttpStatusCode.OK to {
                        description = "Empleado actualizado correctamente"
                        body<EmpleadoDto> { description = "Empleado modificado" }
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el empleado"
                        body<String> { description = "No se ha encontrado el empleado con el id indicado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha indicado el id"
                        body<String> { description = "No se ha indicado el id" }
                    }
                }
            }) {
                logger.debug { "PUT /$ENDPOINT/{id}" }
                try {
                    val id = call.parameters["id"]?.toUUID()!!
                    val dto = call.receive<EmpleadoCreateDto>()
                    val emp = empleadoService.update(id, dto.toModel())
                    call.respond(
                        HttpStatusCode.OK, emp!!.toDTO()
                    )
                } catch (e: EmpleadoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: UUIDException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}", {
                description = "Delete By Id: Borrado de empleado por id"
                request {
                    pathParameter<String>("id") {
                        description = "Id del empleado"
                        required = true
                    }
                }
                response {
                    default {
                        description = "Empleado a eliminar"
                    }
                    HttpStatusCode.NoContent to {
                        description = "Empleado eliminado"
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el empleado"
                        body<String> { description = "No se ha encontrado el empleado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha indicado el id"
                        body<String> { description = "No se ha indicado el id" }
                    }
                }
            }) {
                logger.debug { "DELETE /$ENDPOINT/{id}" }
                try {
                    val id = call.parameters["id"]?.toUUID()!!
                    empleadoService.delete(id)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: EmpleadoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: UUIDException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}