package com.example.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.AuthScheme
import io.github.smiley4.ktorswaggerui.dsl.AuthType
import io.ktor.server.application.*

fun Application.configureSwagger(){
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }
        info {
            title = "Departamento - Empleado Rocio.P.F"
            version = "latest"
            description = "Ejercicio API con Ktor"
            contact {
                name = "Rocío P.F"
                url = "https://github.com/Rochiio"
            }
        }
        server {
            url = environment.config.property("server.baseUrl").getString()
        }

        schemasInComponentSection = true
        examplesInComponentSection = true
        automaticTagGenerator = { url -> url.firstOrNull() }
        pathFilter = { method, url ->
            url.contains("departamentos")
            // Habiltamos el GET para todas y completo para test
            //(method == HttpMethod.Get && url.firstOrNull() == "api")
            // || url.contains("test")
        }


        securityScheme("JWT-Auth") {
            type = AuthType.HTTP
            scheme = AuthScheme.BEARER
            bearerFormat = "jwt"
        }
    }
}