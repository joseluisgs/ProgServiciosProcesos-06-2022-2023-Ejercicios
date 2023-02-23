package com.example.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.TokenConfig
import com.example.models.Empleado
import io.ktor.http.HttpHeaders.Date
import org.koin.core.annotation.Single
import java.util.*

@Single
class TokenService (private val tokenConfig: TokenConfig){


   fun generateJWT(empleado : Empleado) : String{
       return JWT.create()
           .withAudience(tokenConfig.audience)
           .withIssuer(tokenConfig.issuer)
           .withSubject("Seguridad")
           .withClaim("email", empleado.email)
           .withClaim("rol", empleado.rol.toString())
           .withExpiresAt(Date(System.currentTimeMillis()+tokenConfig.expiration*1000))
           .sign(Algorithm.HMAC512(tokenConfig.secret))

   }

    fun validateJWT() : JWTVerifier{
        return JWT.require(Algorithm.HMAC512(tokenConfig.secret))
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .build()
    }
}