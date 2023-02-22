package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.TokenConfig
import com.example.services.tokens.TokensService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val tokenConfigParam = mapOf(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "realm" to environment.config.property("jwt.realm").getString(),
        "expiration" to environment.config.property("jwt.expiration").getString()
    )


    val tokenConfig: TokenConfig = get { parametersOf(tokenConfigParam) }

    val jwtService: TokensService by inject()

    authentication {
        jwt {
            verifier(jwtService.verifyJWT())
            realm = tokenConfig.realm
            validate { credential ->
                if (credential.payload.audience.contains(tokenConfig.audience) &&
                    credential.payload.getClaim("username").asString().isNotEmpty()
                )
                    JWTPrincipal(credential.payload)
                else null
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token invalido o expirado")
            }
        }
    }
}
