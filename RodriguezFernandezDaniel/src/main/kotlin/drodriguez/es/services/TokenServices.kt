package drodriguez.es.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import drodriguez.es.config.TokenConfig
import drodriguez.es.models.User
import org.koin.core.annotation.Single
import java.util.*

@Single
class TokenServices(
    private val config: TokenConfig

) {
    fun createdToken(user: User): String {
        return JWT.create()
            .withAudience(config.audience)
            .withSubject("Authentication")
            .withClaim("username", user.username)
            .withClaim("userId", user.id.toString())
            .withExpiresAt(
                Date(System.currentTimeMillis() + config.expiration * 1000L)
            )
           .sign(Algorithm.HMAC256(config.secret))
    }

    fun verifyJWToken(): JWTVerifier {
        return JWT.require(Algorithm.HMAC256(config.secret))
            .withAudience(config.audience)
           .build()
    }
}