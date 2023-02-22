package sanchez.mireya.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import sanchez.mireya.models.Departamento
import sanchez.mireya.services.departamento.DepartamentosServiceImpl

private const val ENDPOINT = "api/departamentos"

fun Application.departamentosRoutes() {

    val departamentoService: DepartamentosServiceImpl by inject()

    routing {
        route("/$ENDPOINT") {
            get {
                val result = mutableListOf<Departamento>()

                departamentoService.findAll().collect {
                    result.add(it)
                }

                call.respond(HttpStatusCode.OK, result)
            }

            get("{id}") {
                try {
                    val id = call.parameters["id"]!!.toInt()
                    val departamento = departamentoService.findById(id)

                    call.respond(HttpStatusCode.OK, departamento.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            post {
                try {
                    val departamentoReceive = call.receive<Departamento>()
                    val departamentoSave = departamentoService.save(departamentoReceive)
                    call.respond(
                        HttpStatusCode.Created, departamentoService.findById(departamentoSave.id).toString()
                    )
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            put("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()!!
                    val request = call.receive<Departamento>()
                    val departamento = departamentoService.update(id, request)
                    call.respond(HttpStatusCode.OK, departamento.toString())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, e.message.toString())
                }
            }

            delete("{id}") {
                try {
                    val id = call.parameters["id"]?.toInt()!!

                    departamentoService.delete(id)
                    call.respond(HttpStatusCode.NoContent)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.NotFound, e.message.toString())
                }
            }
        }
    }
}