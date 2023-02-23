package com.example.routes

import com.example.exceptions.StorageFileNotFound
import com.example.exceptions.StorageFileNotSaved
import com.example.services.storage.StorageService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.time.LocalDateTime
import java.util.*

fun Application.storageRouting() {
    val storageService: StorageService by inject()
    routing {
        route("/api/storage") {
            get("check") {
                call.respond(
                    HttpStatusCode.OK, mapOf(
                        "status" to "OK",
                        "message" to "Checking Storage",
                        "createdAt" to LocalDateTime.now().toString()
                    )
                )
            }

            post {
                try {
                    val readChannel = call.receiveChannel()
                    val fileName = UUID.randomUUID().toString()
                    val res = storageService.saveFile(fileName, readChannel)
                    call.respond(HttpStatusCode.OK, res)
                } catch (e: StorageFileNotSaved) {
                    call.respond(HttpStatusCode.InternalServerError, e.message.toString())
                }
            }

            get("{fileName}") {
                try {
                    val fileName = call.parameters["fileName"].toString()
                    val file = storageService.getFile(fileName)
                    call.respondFile(file)
                } catch (e: StorageFileNotFound) {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}