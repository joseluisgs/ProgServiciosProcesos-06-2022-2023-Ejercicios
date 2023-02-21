package resa.mario.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single
import resa.mario.conf.TokenConfig
import resa.mario.models.Usuario
import java.util.*

/**
 * Servicio de tokens, en el configuramos la generacion de tokens y de verificarlos.
 *
 * @property tokenConfig
 */
@Single
class TokensService(
    private val tokenConfig: TokenConfig
) {

    fun generateJWT(user: Usuario): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withSubject("Authentication")
            // claims de usuario
            .withClaim("username", user.username)
            .withClaim("userId", user.id.toString())
            // claims de tiempo de expiración milisegundos
            .withExpiresAt(
                Date(System.currentTimeMillis() + tokenConfig.expiration * 1000L)
            )
            .sign(Algorithm.HMAC512(tokenConfig.secret))
    }

    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .build()
    }
}
