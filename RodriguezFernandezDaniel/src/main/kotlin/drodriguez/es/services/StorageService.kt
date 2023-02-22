package drodriguez.es.services

import drodriguez.es.config.StorageConfig
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
                throw Exception("Error al guardar el fichero: ${e.message}")
            }
        }

    suspend fun getFile(name: String): File = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}/$name")

        if (!file.exists()) {
            throw Exception("No se ha encontrado el fichero: $name")
        } else {
            return@withContext file
        }
    }

    suspend fun deleteFile(fileName: String): Unit = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}/$fileName")

        if (!file.exists()) {
            throw Exception("No se ha encontrado el fichero: $fileName")
        } else {
            file.delete()
        }
    }
}