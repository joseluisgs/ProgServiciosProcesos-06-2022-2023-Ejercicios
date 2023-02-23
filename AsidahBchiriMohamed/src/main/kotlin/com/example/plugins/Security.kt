package com.example.plugins

import com.example.config.TokenConfig
import com.example.services.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val tokenConfigParams = mapOf<String, String>(
        "secret" to environment.config.property("jwt.secret").getString(),
        "realm" to environment.config.property("jwt.realm").toString(),
        "expiration" to environment.config.property("jwt.expiration").toString(),
        "issuer" to environment.config.property("jwt.issuer").toString(),
        "audience" to environment.config.property("jwt.audience").getString()
    )

    //inyectar configuracion
    val tokenConfig: TokenConfig = get { parametersOf(tokenConfigParams) }

    //inyectar tokens
    val jwtService: TokenService by inject()

    authentication {
        jwt {
            //cargar verificador
            verifier(jwtService.validateJWT())

            realm = tokenConfig.realm
            validate { jwtCredential ->
                if (jwtCredential.payload.audience.contains(tokenConfig.audience)
                    && jwtCredential.payload.getClaim("email").asString().isNotEmpty()
                )
                    JWTPrincipal(jwtCredential.payload)
                else null
            }

            challenge { defaultScheme, realm -> call.respond(HttpStatusCode.Unauthorized, "Token inválido") }
        }
    }
}
