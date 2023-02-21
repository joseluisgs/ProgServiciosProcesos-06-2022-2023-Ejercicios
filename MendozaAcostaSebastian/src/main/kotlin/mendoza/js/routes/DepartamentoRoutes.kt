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
import kotlinx.coroutines.flow.toList
import mendoza.js.dto.DepartamentoCreateDto
import mendoza.js.dto.DepartamentoDto
import mendoza.js.exceptions.DepartamentoBadRequestException
import mendoza.js.exceptions.DepartamentoNotFoundException
import mendoza.js.mappers.toDTO
import mendoza.js.mappers.toModel
import mendoza.js.service.departamento.DepartamentoService
import mendoza.js.utils.UUIDException
import mendoza.js.utils.toUUID
import mu.KotlinLogging
import org.koin.ktor.ext.inject

private val logger = KotlinLogging.logger { }

private const val ENDPOINT = "api/departamentos"

fun Application.departamentosRoutes() {
    val departamentoService: DepartamentoService by inject()

    routing {
        route("/$ENDPOINT") {
            get({
                description = "Get All departamentos: Lista de departamentos existente"
                response {
                    default {
                        description = "Lista de departamentos"
                    }
                    HttpStatusCode.OK to {
                        description = "Lista de departamentos"
                        body<List<DepartamentoDto>> { description = "Lista de departamentos" }
                    }
                }
            }) {
                logger.debug { "GET ALL /$ENDPOINT" }
                val res = departamentoService.findAll().toList()
                    .map { it.toDTO() }
                    .let { res -> call.respond(HttpStatusCode.OK, res) }
            }

            get("{id}", {
                description = "Get By Id: Buscar departamento por id"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento"
                        required = true
                    }
                }
                response {
                    default {
                        description = "Departamento por Id"
                    }
                    HttpStatusCode.OK to {
                        description = "Departamento con Id indicado"
                        body<DepartamentoDto> { description = "Departamento encontrado" }
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el departamento"
                        body<String> { description = "No existe el departamento con ese Id" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha indicado el id del departamento"
                        body<String> { description = "No se ha indicado el id del departamento a buscar" }
                    }
                }
            }
            ) {
                logger.debug { "GET BY ID /$ENDPOINT/{id}" }
                try {
                    val id = call.parameters["id"]?.toUUID()!!
                    val dpt = departamentoService.findById(id)
                    call.respond(
                        HttpStatusCode.OK, dpt!!.toDTO()
                    )
                } catch (e: DepartamentoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: UUIDException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post({
                description = "Post departamento: Añadir un departamento nuevo"
                request {
                    body<DepartamentoCreateDto> {
                        description = "Departamento a agregar"
                    }
                }
                response {
                    default {
                        description = "Departamento a agregar"
                    }
                    HttpStatusCode.Created to {
                        description = "Departamento añadido"
                        body<DepartamentoDto> { description = "Nuevo departamento añadido" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha podido agregar el departamento"
                        body<String> { description = "Error al agregar el nuevo departamento" }
                    }
                }
            }) {
                logger.debug { "POST /$ENDPOINT" }
                try {
                    val dto = call.receive<DepartamentoCreateDto>()
                    val dpt = departamentoService.save(dto.toModel())
                    call.respond(
                        HttpStatusCode.Created, dpt.toDTO()
                    )
                } catch (e: DepartamentoBadRequestException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}",{
                description = "Put By Id: Actualización de departamento por Id"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento a actualizar"
                        required = true
                    }
                    body<DepartamentoDto> {
                        description = "Departamento a actualizar"
                    }
                }
                response {
                    default {
                        description = "Departamento actualizado"
                    }
                    HttpStatusCode.OK to {
                        description = "Departamento actualizado correctamente"
                        body<DepartamentoDto> { description = "Departamento modificado" }
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el departamento"
                        body<String> { description = "No se ha encontrado el departamento con el Id indicado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha indicado el Id"
                        body<String> { description = "No se ha indicado el Id" }
                    }
                }
            }) {
                logger.debug { "PUT /$ENDPOINT/{id}" }
                try {
                    val id = call.parameters["id"]?.toUUID()!!
                    val dto = call.receive<DepartamentoCreateDto>()
                    val dpt = departamentoService.update(id, dto.toModel())
                    call.respond(
                        HttpStatusCode.OK, dpt.toDTO()
                    )
                } catch (e: DepartamentoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: UUIDException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}", {
                description = "Delete By Id: Borrado de departamento por Id"
                request {
                    pathParameter<String>("id") {
                        description = "Id del departamento"
                        required = true
                    }
                }
                response {
                    default {
                        description = "Departamento a eliminar"
                    }
                    HttpStatusCode.NoContent to {
                        description = "Departamento eliminado"
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el departamento"
                        body<String> { description = "Departamento no encontrado" }
                    }
                    HttpStatusCode.BadRequest to {
                        description = "No se ha indicado el Id"
                        body<String> { description = "No se ha indicado el Id" }
                    }
                }
            }) {
                logger.debug { "DELETE /$ENDPOINT/{id}" }
                try {
                    val id = call.parameters["id"]?.toUUID()!!
                    departamentoService.delete(id)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: DepartamentoNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                } catch (e: UUIDException) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }
        }
    }
}