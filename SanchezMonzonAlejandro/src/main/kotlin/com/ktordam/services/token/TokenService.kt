package com.ktordam.services.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.ktordam.config.TokenConfig
import com.ktordam.models.Usuario
import org.koin.core.annotation.Single
import java.util.*

@Single
class TokenService(
    private val tokenConfig: TokenConfig
) {
    /**
     * Función para generar un token.
     *
     * @param usuario Usuario para el cual generaremos el token.
     */
    fun generateJWT(usuario: Usuario): String {
        return JWT.create()
            .withAudience(tokenConfig.audience)
            .withSubject("Authentication")
            .withClaim("username", usuario.username)
            .withClaim("userId", usuario.uuid.toString())
            .withExpiresAt(
                Date(System.currentTimeMillis() + tokenConfig.expiration * 1000L)
            )
            .sign(Algorithm.HMAC512(tokenConfig.secret))
    }

    /**
     * Función que sirve para verificar que el token introducido es correcto.
     */
    fun verifyJWT(): JWTVerifier {
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .build()
    }
}