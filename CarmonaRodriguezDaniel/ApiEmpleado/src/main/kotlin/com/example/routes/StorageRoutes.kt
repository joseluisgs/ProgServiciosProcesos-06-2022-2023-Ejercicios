package com.example.routes

import com.example.services.storage.StorageService
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.storageRoutes() {
    val storageService: StorageService by inject()

    routing {
        route("/storage") {
            post {
                var newFileName = ""
                val multiPart = call.receiveMultipart()
                multiPart.forEachPart { part ->
                    if (part is PartData.FileItem) {
                        val fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        val fileExtension = fileName.substringAfterLast(".")
                        newFileName = "${UUID.randomUUID()}.$fileExtension"
                        val res = storageService.saveFile(newFileName, fileBytes)
                        newFileName = res["secureUrl"].toString()
                    }
                    part.dispose()
                }
                call.respond(HttpStatusCode.OK, newFileName)
            }
            get("/{fileName}") {
                val fileName = call.parameters["fileName"].toString()
                val file = storageService.getFile(fileName)
                call.respondFile(file)
            }
        }
    }
}