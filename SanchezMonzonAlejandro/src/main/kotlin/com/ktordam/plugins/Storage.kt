package com.ktordam.plugins

import com.ktordam.config.StorageConfig
import com.ktordam.services.storage.StorageService
import io.ktor.server.application.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

/**
 * Función que sirve para configurar el almacenamiento de imágenes.
 */
fun Application.configureStorage() {
    val storageConfigParams = mapOf(
        "uploadDir" to environment.config.property("storage.uploadDir").getString(),
    )

    val storageConfig: StorageConfig = get { parametersOf(storageConfigParams) }
    val storageService: StorageService by inject()

    storageService.initStorageDirectory()
}