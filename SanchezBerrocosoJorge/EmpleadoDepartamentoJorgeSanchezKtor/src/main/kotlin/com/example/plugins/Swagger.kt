package com.example.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.AuthScheme
import io.github.smiley4.ktorswaggerui.dsl.AuthType
import io.ktor.server.application.*

fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }
        info {
            title = "Ktor API REST"
            version = "1.0"
            description = "Ejercicio opcional"
            contact {
                name = "Jorge Sanchez"
                url = "https://github.com/jorgesanchez3212"
            }
        }
        server {
            url = environment.config.property("server.baseUrl").getString()
            description = "Ejercicio opcional"
        }

        schemasInComponentSection = true
        examplesInComponentSection = true
        automaticTagGenerator = { url -> url.firstOrNull() }

        securityScheme("JWT-Auth") {
            type = AuthType.HTTP
            scheme = AuthScheme.BEARER
            bearerFormat = "jwt"
        }
    }
}