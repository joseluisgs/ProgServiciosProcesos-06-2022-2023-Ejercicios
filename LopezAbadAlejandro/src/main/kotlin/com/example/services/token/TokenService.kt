package com.example.services.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.TokenConfig
import com.example.models.Usuario
import org.koin.core.annotation.Single
import java.util.*

@Single
class TokenService(private val tokenConfig: TokenConfig) {


    fun generarJWTToken(usuario: Usuario): String {
        return JWT.create().withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withClaim("nombre", usuario.nombre)
            .withClaim("email", usuario.email)
            .withExpiresAt(Date(System.currentTimeMillis() + tokenConfig.expiration * 10000L))
            .sign(Algorithm.HMAC512(tokenConfig.secret))

    }

    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .build()
    }

}