package resa.mario.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import resa.mario.conf.TokenConfig
import resa.mario.services.TokensService

fun Application.configureSecurity() {

    // Cargamos los parametros del conf
    val tokenConfigParam = mapOf(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "realm" to environment.config.property("jwt.realm").getString()
    )

    val tokenConfig: TokenConfig = get { parametersOf(tokenConfigParam) }

    val jwtService: TokensService by inject()

    authentication {
        // Cargamos el verificador y preparamos la validacion
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
            // En caso de token caducado o invalido
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Not authorized or expired")
            }
        }
    }
}
