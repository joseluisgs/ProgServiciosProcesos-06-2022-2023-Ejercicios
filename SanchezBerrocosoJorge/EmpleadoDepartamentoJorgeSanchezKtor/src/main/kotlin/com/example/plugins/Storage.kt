package com.example.plugins

import com.example.config.StorageConfig
import com.example.services.storage.StorageService
import io.ktor.server.application.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureStorage() {

    val storageConfigParams = mapOf(
        "uploadDir" to environment.config.property("storage.uploadDir").getString(),
    )

    val storageConfig: StorageConfig = get { parametersOf(storageConfigParams) }
    val storageService: StorageService by inject()
    storageService.initStorageDirectory()

}