package resa.mario.plugins

import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.github.smiley4.ktorswaggerui.dsl.AuthScheme
import io.github.smiley4.ktorswaggerui.dsl.AuthType
import io.ktor.server.application.*

fun Application.configureSwagger() {
    // https://github.com/SMILEY4/ktor-swagger-ui/wiki/Configuration
    install(SwaggerUI) {
        swagger {
            swaggerUrl = "swagger"
            forwardRoot = false
        }
        info {
            title = "Ktor Empleado-Departamento API REST"
            version = "1.0"
            description = "Ejercicio opcional empleado-departamento con usuarios"
            contact {
                name = "Mario Resa"
                url = "https://github.com/Mario999X"
            }
        }
        server {
            url = environment.config.property("server.baseUrl").getString()
            description = "Ejercicio opcional empleado-departamento con usuarios"
        }

        schemasInComponentSection = true
        examplesInComponentSection = true
        automaticTagGenerator = { url -> url.firstOrNull() }

        // Se podrian filtrar que rutas se documentan

        securityScheme("JWT-Auth") {
            type = AuthType.HTTP
            scheme = AuthScheme.BEARER
            bearerFormat = "jwt"
        }
    }
}