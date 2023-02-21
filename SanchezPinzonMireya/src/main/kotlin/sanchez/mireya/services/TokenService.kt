package sanchez.mireya.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single
import sanchez.mireya.config.TokenConfig
import sanchez.mireya.models.Usuario
import java.util.*

@Single
class TokenService(
    private val tokenConfig: TokenConfig
) {
    fun generateJWT(user: Usuario): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withSubject("Authentication")
            .withClaim("username", user.username)
            .withClaim("userId", user.id.toString())
            .withExpiresAt(
                Date(System.currentTimeMillis() + tokenConfig.expiration * 3000L)
            )
            .sign(Algorithm.HMAC512(tokenConfig.secret))
    }

    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .build()
    }
}