package resa.mario.plugins

import io.ktor.server.application.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import resa.mario.conf.StorageConfig
import resa.mario.services.StorageService

fun Application.configureStorage() {

    val storageConfigParams = mapOf(
        "uploadDir" to environment.config.property("storage.uploadDir").getString(),
    )

    // Inyectamos la configuración de Storage
    val storageConfig: StorageConfig = get { parametersOf(storageConfigParams) }
    // Inyectamos el servicio de storage
    val storageService: StorageService by inject()
    // Inicializamos el servicio de storage
    storageService.initStorageDirectory()

}