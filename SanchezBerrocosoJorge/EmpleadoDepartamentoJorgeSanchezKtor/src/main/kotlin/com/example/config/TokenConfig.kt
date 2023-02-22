package com.example.config

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single
data class TokenConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val audience = config["audience"].toString()
    val secret = config["secret"].toString()
    val issuer = config["issuer"].toString()
    val realm = config["realm"].toString()
    val expiration = config["expiration"].toString().toLongOrNull() ?: 3600
}