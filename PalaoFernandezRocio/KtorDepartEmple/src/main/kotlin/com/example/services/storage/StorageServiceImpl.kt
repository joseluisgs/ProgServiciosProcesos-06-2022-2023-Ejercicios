package com.example.services.storage

import com.example.config.StorageConfig
import com.example.services.cache.CacheStorageService
import io.ktor.server.sessions.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import org.koin.core.annotation.Single
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Single
class StorageServiceImpl(
    private val storageConfig: StorageConfig,
    private val cacheService: CacheStorageService
): Storage {

    override fun initConfiguration() {
        if(!File(storageConfig.uploadDir).exists()){
            File(storageConfig.uploadDir).mkdir()
        }
    }


    override suspend fun saveFile(fileName: String, channel: ByteReadChannel): Map<String, String> {
        val file = File("${storageConfig.uploadDir}/$fileName")
        val fileResult = channel.copyAndClose(file.writeChannel())
        cacheService.cache.put(UUID.fromString(fileName), file)
        return mapOf(
            "fileName" to fileName,
            "createdAt" to LocalDateTime.now().toString(),
            "size" to fileResult.toString()
        )
    }


    override fun getFile(fileName: String): File? {
        val find = cacheService.cache.get(UUID.fromString(fileName))
        find?.let {
            return it
        }?: run{
            val file = File("${storageConfig.uploadDir}/$fileName")
            if(!file.exists()){
                return null
            }
            return file
        }
    }

    override fun deleteFile(fileName: String) {
        TODO("Not yet implemented")
    }

}