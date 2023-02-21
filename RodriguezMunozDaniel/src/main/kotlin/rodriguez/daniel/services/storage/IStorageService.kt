package rodriguez.daniel.services.storage

import io.ktor.utils.io.*
import rodriguez.daniel.config.StorageConfig
import java.io.File

interface IStorageService {
    fun getConfig(): StorageConfig
    fun initStorageDirectory()
    suspend fun saveFile(fileName: String, fileBytes: ByteArray): Map<String, String>?
    suspend fun saveFile(fileName: String, fileBytes: ByteReadChannel): Map<String, String>?
    suspend fun getFile(fileName: String): File?
    suspend fun deleteFile(fileName: String): Boolean?
}