package com.example.routing

import com.example.repositories.empleado.EmpleadoRepositoryImpl
import com.example.services.storage.StorageServiceImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.storageRouting(){
    val empleados: EmpleadoRepositoryImpl by inject()
    val storageService: StorageServiceImpl by inject()

    val api = "/storage"

    routing {
         route(api){

             authenticate {
                 post {
                     val jwt = call.principal<JWTPrincipal>()
                     val readChannel = call.receiveChannel()
                     val email = jwt?.payload?.getClaim("email").toString().replace("\"","")
                     val find = empleados.findEmpleadoByEmail(email)
                     find?.let {
                         val fileName = it.id.toString()
                         val res = storageService.saveFile(fileName, readChannel)
                         it.avatar = fileName
                         empleados.update(it.id, it)
                         call.respond(HttpStatusCode.Created, res)
                     } ?: run{
                         call.respond(HttpStatusCode.Unauthorized, "Usuario incorrecto")
                     }
                 }


                 get("/{fileName}"){
                     val fileName = call.parameters["fileName"].toString()
                     val file = storageService.getFile(fileName)
                     if(file == null){
                         call.respond(HttpStatusCode.NotFound, "Fichero no encontrado")
                     }else{
                         call.respondFile(file)
                     }
                 }
             }
         }
     }
}