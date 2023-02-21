package resa.mario.services

import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Single
import resa.mario.conf.StorageConfig
import java.io.File
import java.time.LocalDateTime

private val log = KotlinLogging.logger {}

/**
 * Servicio del sistema de almacenamiento, se encarga de verificar el directorio de almacenamiento del programa y demas
 * operaciones relacionadas con ficheros.
 *
 * @property storageConfig
 */
@Single
class StorageService(
    private val storageConfig: StorageConfig
) {

    fun initStorageDirectory() {
        if (!File(storageConfig.uploadDir).exists()) {
            log.info { "Creando directorio de almacenamiento: ${storageConfig.uploadDir}" }
            File(storageConfig.uploadDir).mkdir()
        } else {
            log.debug { "El directorio existe; eliminacion de datos en proceso..." }
            File(storageConfig.uploadDir).listFiles()?.forEach { it.delete() }
        }
    }

    suspend fun saveFile(name: String, fileBytes: ByteReadChannel): Map<String, String> =
        withContext(Dispatchers.IO) {
            try {
                log.debug { "Guardando fichero: $name" }
                val file = File("${storageConfig.uploadDir}/$name")
                val res = fileBytes.copyAndClose(file.writeChannel())
                log.debug { "Fichero guardado en: $file" }

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
        log.debug { "Buscando fichero: $name" }
        val file = File("${storageConfig.uploadDir}/$name")
        log.debug { "Fichero path: $file" }

        if (!file.exists()) {
            throw Exception("No se ha encontrado el fichero: $name")
        } else {
            return@withContext file
        }
    }

    suspend fun deleteFile(fileName: String): Unit = withContext(Dispatchers.IO) {
        log.debug { "Borrando fichero en: $fileName" }
        val file = File("${storageConfig.uploadDir}/$fileName")
        log.debug { "Fichero path: $file" }

        if (!file.exists()) {
            throw Exception("No se ha encontrado el fichero: $fileName")
        } else {
            file.delete()
        }
    }
}