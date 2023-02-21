package com.example.routing

import com.example.dto.UsuarioDto
import com.example.mappers.toUsuario
import com.example.repositories.usuario.UsuarioRepositoryImpl
import com.example.services.password.BcryptService
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

fun Application.usuariosRoutes(){
    val repo: UsuarioRepositoryImpl by inject()
    val bcryptService : BcryptService by inject()
    val apiRoute = "/usuarios"

    routing {
        route(apiRoute){

            get{
                val lista = repo.findAll()
                call.respond(HttpStatusCode.OK, lista)
            }

            get("/{id}"){
                val id = UUID.fromString(call.parameters["id"])
                val find = repo.findById(id)
                find?.let{
                    call.respond(HttpStatusCode.OK, it)
                }?:run{
                    call.respond(HttpStatusCode.NotFound, "Usuario con el id $id no encontrado")
                }
            }


            post {
                try{
                    val dto = call.receive<UsuarioDto>().toUsuario()
                    dto.password = bcryptService.encryptPassword(dto.password)
                    val save = repo.save(dto)
                    call.respond(HttpStatusCode.Created, save)
                }catch (e: RequestValidationException){
                    call.respond(HttpStatusCode.BadRequest, e.reasons)
                }
            }


            put("/{id}"){
                try {
                    val id = UUID.fromString(call.parameters["id"])
                    val dto = call.receive<UsuarioDto>().toUsuario()
                    val find = repo.findById(id)
                    find?.let {
                        dto.id = it.id
                        dto.password = bcryptService.encryptPassword(dto.password)
                        val update = repo.update(dto.id, dto)
                        call.respond(HttpStatusCode.OK, update)
                    }?: run{
                        call.respond(HttpStatusCode.NotFound, "No se ha encontrado usuario con el id: $id")
                    }
                }catch (e: RequestValidationException){
                    call.respond(HttpStatusCode.BadRequest, e.reasons)
                }
            }


            delete("/{id}"){
                val id = UUID.fromString(call.parameters["id"])
                val find = repo.findById(id)
                find?.let {
                    repo.delete(it.id)
                    call.respond(HttpStatusCode.NoContent)
                }?: run{
                    call.respond(HttpStatusCode.NotFound, "No se ha encontrado usuario con el id: $id")
                }
            }


            authenticate {

                get("/me"){
                    val jwt = call.principal<JWTPrincipal>()
                    val email = jwt?.payload?.getClaim("email").toString().replace("\"", "")
                    val find = repo.findByEmail(email)
                    find?.let {
                        call.respond(HttpStatusCode.OK, it)
                    }?: run{
                        call.respond(HttpStatusCode.NotFound, "Problema para encontrar el usuario por el email")
                    }
                }
            }

        }
    }
}