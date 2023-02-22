package com.example.services.tokens

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.TokenConfig
import com.example.models.User
import org.koin.core.annotation.Single
import java.util.*


@Single
class TokensService(
    private val tokenConfig: TokenConfig
) {

    init {
    }

    fun generateJWT(user: User): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withSubject("Authentication")
            // claims de usuario
            .withClaim("username", user.username)
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