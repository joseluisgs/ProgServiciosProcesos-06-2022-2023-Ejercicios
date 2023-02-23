package com.example.services.storage

import com.example.config.StorageConfig
import com.example.exceptions.StorageFileNotFound
import com.example.exceptions.StorageFileNotSaved
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.io.File
import java.time.LocalDateTime

@Single
class StorageService(
    private val storageConfig: StorageConfig
) : IStorageService {
    override  fun getConfig(): StorageConfig {
        return storageConfig
    }

    override  fun initStorageDirectory() {
        if (!File(storageConfig.uploadDir).exists()) {
            File(storageConfig.uploadDir).mkdir()
        } else {
            if (storageConfig.environment == "dev")
                File(storageConfig.uploadDir).listFiles()?.forEach { it.delete() }
        }
    }

    override suspend fun saveFile(fileName: String, fileBytes: ByteArray): Map<String, String> =
        withContext(Dispatchers.IO) {
            try {
                val file = File("${storageConfig.uploadDir}/$fileName")
                file.writeBytes(fileBytes)
                return@withContext mapOf(
                    "fileName" to fileName,
                    "createdAt" to LocalDateTime.now().toString(),
                    "size" to fileBytes.size.toString(),
                    "baseUrl" to "${storageConfig.baseUrl}/${storageConfig.endpoint}/$fileName",
                    "secureUrl" to "${storageConfig.secureUrl}/${storageConfig.endpoint}/$fileName",
                )
            } catch (e: Exception) {
                throw StorageFileNotSaved("No se guardó el archivo")
            }
        }

    override suspend fun saveFile(fileName: String, fileBytes: ByteReadChannel): Map<String, String> =
        withContext(Dispatchers.IO) {
            try {
                val file = File("${storageConfig.uploadDir}/$fileName")
                val res = fileBytes.copyAndClose(file.writeChannel())
                return@withContext mapOf(
                    "fileName" to fileName,
                    "createdAt" to LocalDateTime.now().toString(),
                    "size" to res.toString(),
                    "baseUrl" to "${storageConfig.baseUrl}/${storageConfig.endpoint}/$fileName",
                    "secureUrl" to "${storageConfig.secureUrl}/${storageConfig.endpoint}/$fileName",
                )
            } catch (e: Exception) {
                throw StorageFileNotSaved("No se guardó el archivo")
            }
        }


    override suspend fun getFile(fileName: String): File {
        val file = File("${storageConfig.uploadDir}/$fileName")
        if(file.exists())
            return file
        else
            throw StorageFileNotFound("No se encontró el archivo")
    }

    override suspend fun deleteFile(fileName: String) {
        val file = File("${storageConfig.uploadDir}/$fileName")
        if(file.exists())
            file.delete()
        else
            throw StorageFileNotFound("No se encontró el archivo")
    }
}