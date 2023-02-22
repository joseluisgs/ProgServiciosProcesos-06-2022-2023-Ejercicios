package com.example.config

import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Single


@Single
data class StorageConfig(
    @InjectedParam private val config: Map<String, String>
) {
    val baseUrl = config["baseUrl"].toString()
    val secureUrl = config["secureUrl"].toString()
    val environment = config["environment"].toString()
    val uploadDir = config["uploadDir"].toString()
    val endpoint = config["endpoint"].toString()
}
