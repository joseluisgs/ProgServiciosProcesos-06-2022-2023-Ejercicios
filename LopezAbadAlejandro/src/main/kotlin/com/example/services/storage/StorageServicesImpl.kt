package com.example.services.storage

import com.example.config.StorageConfig
import com.example.services.cache.CacheService
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import org.koin.core.annotation.Single
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Single
class StorageServicesImpl(
    private val config: StorageConfig,
    private val cach: CacheService
) : Storage {


    override fun initConfiguration() {
        if (!File(config.uploadDir).exists()) {
            File(config.uploadDir).mkdir()
        }
    }

    override suspend fun saveFile(name: String, channel: ByteReadChannel): Map<String, String> {
        val file = File("${config.uploadDir}/$name")

        val res = channel.copyAndClose(file.writeChannel())
        cach.cach.put(UUID.fromString(name), file)
        return mapOf(
            "fileName" to name,
            "createdAt" to LocalDateTime.now().toString(),
            "size" to res.toString()
        )
    }

    override fun getFile(name: String): File? {
        val get = cach.cach.get(UUID.fromString(name))
        get?.let {
            return it
        } ?: run {
            val file = File("${config.uploadDir}/$name")
            if(!file.exists()){
                return null
            }
            return file
        }
    }

}


