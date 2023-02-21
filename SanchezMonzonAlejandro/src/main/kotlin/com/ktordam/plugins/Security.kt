package com.ktordam.plugins

import com.ktordam.config.TokenConfig
import com.ktordam.services.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

/**
 * Función que sirve para configurar la seguridad de la API con JWT.
 */
fun Application.configureSecurity() {
    val tokenParams = mapOf<String, String>(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "realm" to environment.config.property("jwt.realm").getString()
    )

    val tokenConfig: TokenConfig = get { parametersOf(tokenParams) }

    val jwtService by inject<TokenService>()

    authentication {
        jwt {
            verifier(jwtService.verifyJWT())

            realm = tokenConfig.realm

            validate {
                if (it.payload.audience.contains(tokenConfig.audience) &&
                    it.payload.getClaim("username").asString().isNotEmpty()
                ) {
                    JWTPrincipal(it.payload)
                } else null
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token inválido o caducado.")
            }
        }
    }
}
