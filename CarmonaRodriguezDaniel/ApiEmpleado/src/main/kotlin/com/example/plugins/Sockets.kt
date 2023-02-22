package com.example.plugins

import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json

fun Application.configureSockets() {
    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}
