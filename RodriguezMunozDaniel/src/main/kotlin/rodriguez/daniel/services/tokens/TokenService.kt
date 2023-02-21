package rodriguez.daniel.services.tokens

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.annotation.Single
import rodriguez.daniel.config.TokenConfig
import rodriguez.daniel.model.User
import java.util.*

@Single
class TokenService(
    private val tokenConfig: TokenConfig
) {
    init { println("Initializing token service: ${tokenConfig.audience}") }

    fun generateJWT(entity: User): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withSubject("Authentication")
            .withClaim("id", entity.id.toString())
            .withClaim("email", entity.email)
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