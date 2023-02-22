package com.example.services.storage

import com.example.config.StorageConfig
import com.example.exceptions.StorageFileNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.io.File
import java.time.LocalDateTime

@Single
class StorageServiceImpl(
    private val storageConfig: StorageConfig,
) : StorageService {
    override fun getConfig(): StorageConfig {
        return storageConfig
    }

    override fun initStorageDirectory() {
        if (!File(storageConfig.uploadDir).exists()) {
            File(storageConfig.uploadDir).mkdir()
        }
    }

    override suspend fun saveFile(fileName: String, fileBytes: ByteArray): Map<String, String> =
        withContext(Dispatchers.IO) {
            val file = File(storageConfig.uploadDir + File.separator + fileName)
            file.writeBytes(fileBytes)
            return@withContext mapOf(
                "fileName" to fileName,
                "createAt" to LocalDateTime.now().toString(),
                "size" to fileBytes.size.toString(),
                "secureUrl" to storageConfig.secureUrl + "/" + storageConfig.endpoint + "/" + fileName,
            )
        }

    override suspend fun getFile(fileName: String): File = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}/$fileName")
        if (!file.exists()) {
            throw StorageFileNotFoundException("No se ha encontrado el fichero: $fileName")
        } else {
            return@withContext file
        }
    }

    override suspend fun deleteFile(fileName: String): Unit = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}/$fileName")
        if (!file.exists()) {
            throw StorageFileNotFoundException("No se ha encontrado el fichero: $fileName")
        } else {
            file.delete()
        }
    }
}