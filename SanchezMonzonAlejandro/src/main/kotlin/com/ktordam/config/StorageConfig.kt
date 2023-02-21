package com.ktordam.config

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

/**
 * Data class para configurar el servicio de Storage.
 */
@Single
data class StorageConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val uploadDir = config["uploadDir"].toString()
}