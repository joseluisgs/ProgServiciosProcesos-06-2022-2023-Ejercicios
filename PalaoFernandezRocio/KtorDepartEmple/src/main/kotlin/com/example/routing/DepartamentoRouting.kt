package com.example.routing

import com.example.dto.DepartamentoDto
import com.example.mappers.toDepartamento
import com.example.models.Departamento
import io.github.smiley4.ktorswaggerui.dsl.*
import com.example.models.Rol
import com.example.repositories.departamento.DepartamentoRepositoryImpl
import com.example.repositories.empleado.EmpleadoRepositoryImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.departamentoRoutes(){
    val repo: DepartamentoRepositoryImpl by inject()
    val empleados: EmpleadoRepositoryImpl by inject()


    routing {

        get("/departamentos",{
            description = "Get All Departamentos"
            response {
                HttpStatusCode.OK to {
                    description = "Listas de departamentos"
                    body<List<Departamento>> { description = "Lista de mensajes de test" }
                }
            }
        }){

            call.respond(HttpStatusCode.OK, repo.findAll())
        }


        get("/departamentos/{id}", {
            description = "Find By Id Departamento"
            request {
                pathParameter<String>("id") {
                    description = "Id del departamento a buscar"
                    required = true
                }
            }
            response {
                HttpStatusCode.OK to {
                    description = "Departamento con el id indicado"
                    body<Departamento> { description = "Departamento encontrado" }
                }
                HttpStatusCode.NotFound to {
                    description = "No se ha encontrado el departamento"
                    body<String> { description = "El id no existe" }
                }
                // Y así con todos los códigos de respuesta
            }
        }){

            val id = UUID.fromString(call.parameters["id"])
            val departamento = repo.findById(id)
            departamento?.let {
                call.respond(HttpStatusCode.OK, it)
            }?:run{
                call.respond(HttpStatusCode.NotFound, "Departamento con $id no encontrado")
            }

        }


        post("/departamentos",{
            description = "Post Departamento"
            request {
                body<DepartamentoDto> {
                    description = "Departamento que queremos crear"
                }
            }
            response {
                HttpStatusCode.Created to {
                    description = "Departamento añadido"
                    body<Departamento> { description = "Departamento que ha sido añadido" }
                }
                HttpStatusCode.BadRequest to{
                    description = "Validacion del departamento a crear incorrecto"
                }
            }

        }){
            try{
                val post = call.receive<DepartamentoDto>().toDepartamento()
                val save = repo.save(post)
                call.respond(HttpStatusCode.Created, save)
            }catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }


        put("/departamentos/{id}",{
            description = "Put Departamento"
            request {
                pathParameter<String>("id") {
                    description = "Id del departamento a actualizar"
                    required = true
                }
                body<DepartamentoDto> {
                    description = "Datos departamento a cambiar"
                }
            }
            response {
                HttpStatusCode.OK to{
                    description = "Departamento actualizado correctamente"
                    body<Departamento> {description = "Departamento actualizado"}
                }
                HttpStatusCode.NotFound to {
                    description= "Cuando no esncontramos el servidor del id indicado"
                }
                HttpStatusCode.BadRequest to{
                    description ="Cuando la validacion ha sido incorrecta"
                }
            }
        }){
            try {
                val id = UUID.fromString(call.parameters["id"])
                val dto = call.receive<DepartamentoDto>().toDepartamento()
                val departamento = repo.findById(id)
                departamento?.let {
                    val update = repo.update(id, dto)
                    call.respond(HttpStatusCode.OK, update)
                } ?: run {
                    call.respond(HttpStatusCode.NotFound, "No se ha encontrado un Departamento con el id: $id")
                }
            }catch(e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }



        authenticate {

            delete("/departamentos/{id}", {
                description = "Delete departamento, se necesita ser admin para ello"
                request {
                    pathParameter<String>("id"){
                        description = "Id del departamento a eliminar"
                        required = true
                    }
                    authenticate {
                    }
                }
                response{
                    HttpStatusCode.NoContent to {
                        description="El departamento ha sido eliminado correctamente"
                    }
                    HttpStatusCode.NotFound to {
                        description = "No se ha encontrado el departamento con ese id"
                    }
                    HttpStatusCode.Unauthorized to {
                        description = "No estás autorizado para eliminar el departamento"
                    }
                }
            }){

                val id = UUID.fromString(call.parameters["id"])
                val jwt = call.principal<JWTPrincipal>()
                val email = jwt?.payload?.getClaim("email").toString().replace("\"", "")
                val findEmple = empleados.findEmpleadoByEmail(email)
                findEmple?.let {
                    if (it.rol == Rol.ADMIN) {
                        println(findEmple)
                        val departamento = repo.findById(id)
                        departamento?.let {
                            val lista = empleados.getEmpleadosByIdDepartamento(departamento.id)
                            if (lista.isNotEmpty()) {
                                call.respond(HttpStatusCode.BadRequest, "Este departamento contiene empleados")
                            } else {
                                repo.delete(id)
                                call.respond(HttpStatusCode.NoContent)
                            }
                        } ?: run {
                            call.respond(HttpStatusCode.NotFound, "No se ha encontrado un Departamento con el id: $id")
                        }

                    }else{
                        call.respond(HttpStatusCode.Unauthorized, "No tiene los permisos necesarios para realizar esta accion")
                    }
                } ?: run{
                    call.respond(HttpStatusCode.Unauthorized, "No tiene los permisos necesarios para realizar esta accion")
                }

            }

        }
    }
}