package mendoza.js.plugins

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
            title = "Empresa Ktor Rest"
            version = "lastes"
            description = "API Rest usando Ktor y tecnologías Kotlin"
            contact {
                name = "Sebastián Mendoza Acosta"
                url = "https://github.com/SebsMendoza"
            }
        }
        server {
            url = environment.config.property("server.baseUrl").getString()
            description = "Servidor de la API Rest usando Ktor y tecnologías Kotlin"
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