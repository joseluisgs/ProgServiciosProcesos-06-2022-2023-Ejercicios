package es.ruymi.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import es.ruymi.config.TokenConfig
import io.ktor.server.application.*
import es.ruymi.services.tokens.TokensService
import io.ktor.http.*
import io.ktor.server.response.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    // Leemos la configuración de tokens de nuestro fichero de configuración
    val tokenConfigParams = mapOf<String, String>(
        "audience" to environment.config.property("jwt.audience").getString(),
        "secret" to environment.config.property("jwt.secret").getString(),
        "issuer" to environment.config.property("jwt.issuer").getString(),
        "realm" to environment.config.property("jwt.realm").getString(),
        "expiration" to environment.config.property("jwt.expiration").getString()
    )

    // Inyectamos la configuración de Tokens
    val tokenConfig: TokenConfig = get { parametersOf(tokenConfigParams) }

    // Inyectamos el servicio de tokens
    val jwtService: TokensService by inject()

    authentication {
        jwt {
            // Cargamos el verificador con los datos de la configuracion
            verifier(jwtService.verifyJWT())
            // con realm aseguramos la ruta que estamos protegiendo
            realm = tokenConfig.realm
            validate { credential ->
                // Si el token es valido, ademas tiene la udiencia indicada,
                // y tiene el campo del usuario para compararlo con el que nosotros queremos
                // devolvemos el JWTPrincipal, si no devolvemos null
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
