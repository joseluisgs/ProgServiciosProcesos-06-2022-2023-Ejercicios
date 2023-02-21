package com.ktordam.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.AuthScheme
import io.github.smiley4.ktorswaggerui.dsl.AuthType
import io.ktor.server.application.*

/**
 * Función que sirve para configurar Swagger.
 */
fun Application.configureSwagger() {
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }
        info {
            title = "Repaso-PSP: Ktor API"
            version = "1.0"
            description = "Ejercicio de empleados-departamento para Ktor"
            contact {
                name = "Alejandro Sánchez"
                url = "https://github.com/AlejandroSanchezMonzon"
            }
        }
        server {
            url = environment.config.property("server.baseUrl").getString()
            description = "Ejercicio de empleados-departamento para Ktor"
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