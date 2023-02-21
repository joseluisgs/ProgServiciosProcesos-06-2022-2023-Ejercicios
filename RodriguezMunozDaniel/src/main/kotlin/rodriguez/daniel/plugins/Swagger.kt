package rodriguez.daniel.plugins

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
            title = "Ejercicio Ktor de loli"
            version = "latest"
            description = "Ejercicio opcional de ktor. API Rest reactiva (la base de datos es un mapa) usando JWT y Koin."
            contact {
                name = "Daniel Rodriguez Muñoz"
                url = "https://github.com/idliketobealoli"
            }
        }
        server {
            url = environment.config.property("server.baseUrl").getString()
            description = "Servidor de la API Rest."
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