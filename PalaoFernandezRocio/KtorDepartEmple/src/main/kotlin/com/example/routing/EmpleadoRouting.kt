package com.example.routing

import com.example.dto.EmpleadoDto
import com.example.mappers.toEmpleado
import com.example.models.Departamento
import com.example.models.Empleado
import com.example.models.Rol
import com.example.repositories.departamento.DepartamentoRepository
import com.example.repositories.departamento.DepartamentoRepositoryImpl
import com.example.repositories.empleado.EmpleadoRepositoryImpl
import com.example.services.password.BcryptService
import com.example.services.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun Application.empleadoRoutes(){
    val repo: EmpleadoRepositoryImpl by inject()
    val departamento: DepartamentoRepositoryImpl by inject()
    val bcryptService: BcryptService by inject()

    routing {

        get("/empleados"){
            call.respond(HttpStatusCode.OK, repo.findAll())
        }


        get("/empleados/{id}"){
            val id = UUID.fromString(call.parameters["id"])
            val encontrado = repo.findById(id)
            encontrado?.let {
                call.respond(HttpStatusCode.OK, it)
            }?: run{
                call.respond(HttpStatusCode.NotFound, "Empleado con el id $id no encontrado")
            }
        }


        post("/empleados"){
            try{
                val post = call.receive<EmpleadoDto>().toEmpleado()
                val findDpt = departamento.findById(post.idDepartamento)
                findDpt?.let {
                    post.password = bcryptService.encryptPassword(post.password)
                    val save = repo.save(post)
                    call.respond(HttpStatusCode.Created, save)
                }?: run{
                    call.respond(HttpStatusCode.NotFound, "idDepartmaento inválido")
                }
            }catch (e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }


        put("/empleados/{id}"){
            try {
                val id = UUID.fromString(call.parameters["id"])
                val dto = call.receive<EmpleadoDto>().toEmpleado()
                val find = repo.findById(id)
                find?.let {
                    dto.id = it.id
                    dto.password = bcryptService.encryptPassword(dto.password)
                    val update = repo.update(id, dto)
                    call.respond(HttpStatusCode.OK, update)
                } ?: run {
                    call.respond(HttpStatusCode.NotFound, "No se ha encontrado un Empleado con el id: $id")
                }
            }catch(e: RequestValidationException){
                call.respond(HttpStatusCode.BadRequest, e.reasons)
            }
        }


        delete("/empleados/{id}"){
            val id = UUID.fromString(call.parameters["id"])
            val find = repo.findById(id)
            find?.let {
                repo.delete(it.id)
                call.respond(HttpStatusCode.NoContent)
            }?:run{
                call.respond(HttpStatusCode.NotFound,"Empleado con id $id no encontrado")
            }
        }


        authenticate {
            get("/empleados/me"){
                val jwt = call.principal<JWTPrincipal>()
                val email = jwt?.payload?.getClaim("email").toString().replace("\"", "")
                val find = repo.findEmpleadoByEmail(email)
                find?.let {
                    call.respond(HttpStatusCode.OK, it)
                }?: run{
                    call.respond(HttpStatusCode.NotFound, "Problema para encontrar el empleado por el email")
                }
            }

        }



    }
}