package resa.mario.conf

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

/**
 * Clase de configuracion del servicio de almacenamiento.
 *
 * @property config
 */
@Single
data class StorageConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val uploadDir = config["uploadDir"].toString()
}