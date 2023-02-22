package com.example.plugins

import com.example.config.StorageConfig
import com.example.services.storage.Storage
import io.ktor.server.application.*
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configStorage(){

    val config = mapOf(
        "uploadDir" to environment.config.property("storage.uploadDir").getString(),
        "endpoint" to environment.config.property("storage.endpoint").getString()
    )
    val configStorage: StorageConfig = get{ parametersOf(config) }
   val serviceStorage: Storage by inject()

    serviceStorage.initConfiguration()
}