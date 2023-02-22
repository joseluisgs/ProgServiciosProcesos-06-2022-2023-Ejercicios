package es.ruymi.plugins

import es.ruymi.config.StorageConfig
import io.ktor.server.application.*
import es.ruymi.services.storage.StorageService
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureStorage() {
    val storageConfigParams = mapOf(
        "baseUrl" to environment.config.property("server.baseUrl").getString(),
        "secureUrl" to environment.config.property("server.baseSecureUrl").getString(),
        "environment" to environment.config.property("ktor.environment").getString(),
        "uploadDir" to environment.config.property("storage.uploadDir").getString(),
        "endpoint" to environment.config.property("storage.endpoint").getString()
    )

    val storageConfig: StorageConfig = get { parametersOf(storageConfigParams) }
    val storageService: StorageService by inject()
    storageService.initStorageDirectory()

}