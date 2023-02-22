package drodriguez.es.config

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

@Single
data class TokenConfig(
    @InjectedParam private val conf: Map<String, String>
) {
    val audience = conf["audience"].toString()
    val secret = conf["secret"].toString()
    val realm = conf["realm"].toString()
    val expiration = conf["expiration"].toString().toLongOrNull() ?: 3600
}