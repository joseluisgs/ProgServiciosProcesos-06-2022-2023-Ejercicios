package es.ruymi.routes

import es.ruymi.dto.DepartamentoDTO
import es.ruymi.exceptions.DepartamentoNotFoundException
import es.ruymi.exceptions.UUIDException
import es.ruymi.exceptions.UserNotFoundException
import es.ruymi.mappers.toDto
import es.ruymi.mappers.toEntity
import es.ruymi.services.departamento.DepartamentoService
import es.ruymi.services.users.UsersService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.toList
import mu.KotlinLogging
import org.koin.ktor.ext.inject
import java.util.UUID

private val logger = KotlinLogging.logger {}

private const val ENDPOINT = "api/departamento"

fun Application.departamentoRoutes() {
    val departamentoService: DepartamentoService by inject()
    val usersService: UsersService by inject()

    routing {
        route("/$ENDPOINT") {
            authenticate {
                get("/") {
                    logger.debug { "GET Me /$ENDPOINT/" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("username")
                            .toString().replace("\"", "")
                        val user = usersService.findByUsername(userId)
                        val res = departamentoService.findAll().toList().map {
                            it.toDto()
                        }
                        call.respond(HttpStatusCode.OK, res)

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    }
                }

                get("/{id}") {
                    logger.debug { "GET BY ID /$ENDPOINT/{id}" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("username")
                            .toString().replace("\"", "")
                        val user = usersService.findByUsername(userId)
                        val id = UUID.fromString(call.parameters["id"])
                        val departamento = departamentoService.findById(id)
                        call.respond(
                            HttpStatusCode.OK, departamento!!.toDto()
                        )
                    } catch (e: DepartamentoNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    } catch (e: UUIDException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }

                post("/") {
                    logger.debug { "POST /$ENDPOINT" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("username")
                            .toString().replace("\"", "")
                        val user = usersService.findByUsername(userId)
                        val dto = call.receive<DepartamentoDTO>()
                        val departamento = departamentoService.save(dto.toEntity())
                        call.respond(
                            HttpStatusCode.Created, departamento.toDto()
                        )
                    } catch (e: DepartamentoNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }
                }

                put("{id}") {
                    logger.debug { "PUT /$ENDPOINT/{id}" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("username")
                            .toString().replace("\"", "")
                        val user = usersService.findByUsername(userId)
                        val dto = call.receive<DepartamentoDTO>()
                        val departamento = departamentoService.update( dto.toEntity())
                        call.respond(
                            HttpStatusCode.OK, departamento!!.toDto()
                        )
                    } catch (e: DepartamentoNotFoundException) {
                        call.respond(HttpStatusCode.NotFound, e.message.toString())
                    }  catch (e: UUIDException) {
                        call.respond(HttpStatusCode.BadRequest, e.message.toString())
                    }
                }
                delete("{id}") {
                    logger.debug { "DELETE /$ENDPOINT/{id}" }
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("username")
                            .toString().replace("\"", "")
                        val user = usersService.findByUsername(userId)
                        val id = UUID.fromString(call.parameters["id"])
                        val departamento = departamentoService.delete(id)
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


}