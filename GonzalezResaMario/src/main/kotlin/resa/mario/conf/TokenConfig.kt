package resa.mario.conf

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

/**
 * Clase de configuracion del servicio de tokens.
 *
 * @property config
 */
@Single
data class TokenConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val audience = config["audience"].toString()
    val secret = config["secret"].toString()
    val realm = config["realm"].toString()
    val expiration = config["expiration"].toString().toLongOrNull() ?: 3600

}
