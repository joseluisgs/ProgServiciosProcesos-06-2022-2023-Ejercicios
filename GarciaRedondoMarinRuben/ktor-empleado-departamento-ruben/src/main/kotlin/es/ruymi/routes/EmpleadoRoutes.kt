package es.ruymi.routes

import es.ruymi.dto.DepartamentoDTO
import es.ruymi.dto.EmpleadoDTO
import es.ruymi.exceptions.DepartamentoNotFoundException
import es.ruymi.exceptions.EmpleadoNotFoundException
import es.ruymi.exceptions.UUIDException
import es.ruymi.exceptions.UserNotFoundException
import es.ruymi.mappers.toDto
import es.ruymi.mappers.toEntity
import es.ruymi.services.empleados.EmpleadoService
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

private const val ENDPOINT = "api/empleado"

fun Application.empleadoRoutes() {
    val empleadoService: EmpleadoService by inject()
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
                        val res = empleadoService.findAll().toList().map {
                            it.toDto()
                        }
                        call.respond(HttpStatusCode.OK, res)

                    } catch (e: UserNotFoundException) {
                        call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado o no autenticado")
                    }
                }

                get("/{id}") {
                    logger.debug { "GET BY ID /$ENDPOINT/{id}" }
                    // Obtenemos el id
                    try {
                        val jwt = call.principal<JWTPrincipal>()
                        val userId = jwt?.payload?.getClaim("username")
                            .toString().replace("\"", "")
                        val user = usersService.findByUsername(userId)
                        val id = UUID.fromString(call.parameters["id"])
                        val departamento = empleadoService.findById(id)
                        call.respond(
                            HttpStatusCode.OK, departamento!!.toDto()
                        )
                    } catch (e: EmpleadoNotFoundException) {
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
                        val dto = call.receive<EmpleadoDTO>()
                        val Empleado = empleadoService.save(dto.toEntity())
                        call.respond(
                            HttpStatusCode.Created, Empleado.toDto()
                        )
                    } catch (e: EmpleadoNotFoundException) {
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
                        val dto = call.receive<EmpleadoDTO>()
                        val Empleado = empleadoService.update( dto.toEntity())
                        call.respond(
                            HttpStatusCode.OK, Empleado!!.toDto()
                        )
                    } catch (e: EmpleadoNotFoundException) {
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
                        val Empleado = empleadoService.delete(id)
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


}