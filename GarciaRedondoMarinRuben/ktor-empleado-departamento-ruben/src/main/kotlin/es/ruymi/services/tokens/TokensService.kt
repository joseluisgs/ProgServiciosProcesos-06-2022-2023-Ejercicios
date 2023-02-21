package es.ruymi.services.tokens

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import es.ruymi.config.TokenConfig
import es.ruymi.models.User
import mu.KotlinLogging
import org.koin.core.annotation.Single
import java.util.*

private val logger = KotlinLogging.logger {}

@Single
class TokensService(
    private val tokenConfig: TokenConfig
) {

    init {
        logger.debug { "Iniciando servicio de tokens con audience: ${tokenConfig.audience}" }
    }

    fun generateJWT(user: User): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withSubject("Authentication")
            .withClaim("username", user.usuario)
            .withClaim("email", user.correo)
            .withClaim("userId", user.id.toString())
            .withExpiresAt(
                Date(System.currentTimeMillis() + tokenConfig.expiration * 1000L)
            )
            .sign(Algorithm.HMAC512(tokenConfig.secret))
    }

    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .build()
    }
}