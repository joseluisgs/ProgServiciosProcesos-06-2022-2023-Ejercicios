package com.example.services.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.TokenConfig
import com.example.models.Empleado
import com.example.models.Usuario
import org.koin.core.annotation.Single
import java.util.*

@Single
class TokenService(
    private val config: TokenConfig
) {
    fun generarToken(usuario: Usuario): String{
        return JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withClaim("email",usuario.email)
            .withClaim("rol", usuario.rol.name)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiration * 10000L ))
            .sign(Algorithm.HMAC512(config.secret))
    }

    fun generarTokenEmpleado(empleado: Empleado): String{
        return JWT.create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withClaim("email",empleado.email)
            .withClaim("rol", empleado.rol.name)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiration * 10000L ))
            .sign(Algorithm.HMAC512(config.secret))
    }

    fun verificarToken():JWTVerifier{
        return JWT.require(Algorithm.HMAC512(config.secret))
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .build()
    }
}