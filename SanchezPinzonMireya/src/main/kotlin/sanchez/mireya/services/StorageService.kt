package sanchez.mireya.services

import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import sanchez.mireya.config.StorageConfig
import java.io.File
import java.time.LocalDateTime


@Single
class StorageService(
    private val storageConfig: StorageConfig
) {

    fun initStorageDirectory() {
        if (!File(storageConfig.uploadDir).exists()) {
            File(storageConfig.uploadDir).mkdir()
        } else {
            File(storageConfig.uploadDir).listFiles()?.forEach { it.delete() }
        }
    }

    suspend fun saveFile(name: String, fileBytes: ByteReadChannel): Map<String, String> =
        withContext(Dispatchers.IO) {
            try {
                val file = File("${storageConfig.uploadDir}/$name")
                val res = fileBytes.copyAndClose(file.writeChannel())

                return@withContext mapOf(
                    "fileName" to name,
                    "createdAt" to LocalDateTime.now().toString(),
                    "size" to res.toString(),
                )
            } catch (e: Exception) {
                throw Exception("Error saving: ${e.message}")
            }
        }

    suspend fun getFile(name: String): File = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}/$name")

        if (!file.exists()) {
            throw Exception("Not found: $name")
        } else {
            return@withContext file
        }
    }

    suspend fun deleteFile(fileName: String): Unit = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}/$fileName")

        if (!file.exists()) {
            throw Exception("Not found: $fileName")
        } else {
            file.delete()
        }
    }
}