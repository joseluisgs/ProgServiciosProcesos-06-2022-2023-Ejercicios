package com.example.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.TokenConfig
import com.example.models.User
import org.koin.core.annotation.Single
import java.util.*

@Single
class TokenService(
    private val tokenConfig: TokenConfig,
) {
    fun generateJWT(user: User): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withSubject("Authentication")
            .withClaim("username", user.username)
            .withClaim("rol", user.role.name)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.expiration * 10000))
            .sign(Algorithm.HMAC512(tokenConfig.secret))
    }

    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .build()
    }
}