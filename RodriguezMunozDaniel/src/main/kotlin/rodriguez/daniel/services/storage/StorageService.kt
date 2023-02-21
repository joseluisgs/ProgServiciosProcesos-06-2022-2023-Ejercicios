package rodriguez.daniel.services.storage

import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import rodriguez.daniel.config.StorageConfig
import java.io.File
import java.time.LocalDateTime

@Single
class StorageService(
    private val storageConfig: StorageConfig
): IStorageService {
    override fun getConfig(): StorageConfig {
        return storageConfig
    }

    override fun initStorageDirectory() {
        println("Initializing storage directory in path: ${storageConfig.uploadDir}")
        val file = File(storageConfig.uploadDir)
        if (!file.exists()) {
            println("Creating storage directory.")
            file.mkdir()
        }
        else if(storageConfig.environment == "dev") {
            println("Dev mode detected. Deleting contents.")
            file.listFiles()?.forEach { it.delete() }
        }
    }

    override suspend fun saveFile(fileName: String, fileBytes: ByteArray): Map<String, String>? = withContext(Dispatchers.IO) {
        try {
            val file = File("${storageConfig.uploadDir}${File.separator}$fileName")
            file.writeBytes(fileBytes)

            mapOf(
                "fileName" to fileName,
                "createdAt" to LocalDateTime.now().toString(),
                "size" to fileBytes.size.toString(),
                "baseUrl" to "${storageConfig.baseUrl}${File.separator}${storageConfig.endpoint}${File.separator}$fileName",
                "secureUrl" to "${storageConfig.secureUrl}${File.separator}${storageConfig.endpoint}${File.separator}$fileName"
            )
        } catch (e: Exception) {
            println("could not save in $fileName. ${e.message}")
            null
        }
    }

    override suspend fun saveFile(fileName: String, fileBytes: ByteReadChannel): Map<String, String>? = withContext(Dispatchers.IO) {
        try {
            val file = File("${storageConfig.uploadDir}${File.separator}$fileName")
            val res = fileBytes.copyAndClose(file.writeChannel())

            mapOf(
                "fileName" to fileName,
                "createdAt" to LocalDateTime.now().toString(),
                "size" to res.toString(),
                "baseUrl" to "${storageConfig.baseUrl}${File.separator}${storageConfig.endpoint}${File.separator}$fileName",
                "secureUrl" to "${storageConfig.secureUrl}${File.separator}${storageConfig.endpoint}${File.separator}$fileName"
            )
        } catch (e: Exception) {
            println("could not save in $fileName. ${e.message}")
            null
        }
    }

    override suspend fun getFile(fileName: String): File? = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}${File.separator}$fileName")
        if (!file.exists()) {
            println("file with name $fileName not found")
            null
        } else file
    }

    override suspend fun deleteFile(fileName: String): Boolean? = withContext(Dispatchers.IO) {
        val file = File("${storageConfig.uploadDir}${File.separator}$fileName")
        if (!file.exists()) {
            println("file with name $fileName not found")
            null
        }
        else file.delete()
    }
}