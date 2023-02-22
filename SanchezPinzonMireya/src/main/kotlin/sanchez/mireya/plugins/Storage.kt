package sanchez.mireya.plugins

import io.ktor.server.application.*
import org.koin.core.parameter.parametersOf
import sanchez.mireya.config.StorageConfig
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import sanchez.mireya.services.StorageService

fun Application.configureStorage(){
    val storageConfigParams = mapOf(
        "uploadDir" to environment.config.property("storage.uploadDir").getString(),
    )

    val storageConfig: StorageConfig = get { parametersOf(storageConfigParams) }
    val storageService: StorageService by inject()
    storageService.initStorageDirectory()

}