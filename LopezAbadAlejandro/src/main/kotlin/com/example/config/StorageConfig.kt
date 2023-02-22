package com.example.config

import mu.KotlinLogging
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger {}

@Single
data class StorageConfig(
    @InjectedParam private val config: Map<String, String>
) {

    val uploadDir = config["uploadDir"].toString()
    val endpoint = config["endpoint"].toString()

    init {
        logger.debug { "Iniciando la configuración de Storage" }
    }
}