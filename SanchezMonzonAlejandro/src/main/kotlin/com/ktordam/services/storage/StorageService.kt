package com.ktordam.services.storage

import com.ktordam.config.StorageConfig
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Single
import java.io.File
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Single
class StorageService(
    private val storageConfig: StorageConfig
) {
    /**
     * Función que inicia el directorio para guardar las imágenes.
     */
    fun initStorageDirectory() {
        if (!File(storageConfig.uploadDir).exists()) {
            logger.info { "Directorio de storage: ${storageConfig.uploadDir}" }
            File(storageConfig.uploadDir).mkdir()
        } else {
            logger.error { "El directorio existe, realizando operaciones..." }
            File(storageConfig.uploadDir).listFiles()?.forEach { it.delete() }
        }
    }

    /**
     * Función que guarda las imagenes del avatar.
     *
     * @param nombre Nombre del fichero.
     * @param fileBytes Canal de lectura.
     */
    suspend fun saveFile(nombre: String, fileBytes: ByteReadChannel): Map<String, String> = withContext(Dispatchers.IO) {
        try {
            logger.debug { "Guardando fichero..." }
            val file = File("${storageConfig.uploadDir}/$nombre")
            val res = fileBytes.copyAndClose(file.writeChannel())
            logger.debug { "Fichero guardado: $file" }

            return@withContext mapOf(
                "fileName" to nombre,
                "createdAt" to LocalDateTime.now().toString(),
                "size" to res.toString(),
            )
        } catch (e: Exception) {
            throw Exception("Error al guardar el fichero: ${e.message}")
        }
    }

    /**
     * Función que sirve para obtener un fichero.
     *
     * @param nombre Fichero a obtener.
     */
    suspend fun getFile(nombre: String): File = withContext(Dispatchers.IO) {
        logger.debug { "Buscando fichero..." }
        val file = File("${storageConfig.uploadDir}/$nombre")
        logger.debug { "Fichero: $file" }

        if (!file.exists()) {
            throw Exception("Fichero $nombre no encontrado.")
        } else {
            return@withContext file
        }
    }

    /**
     * Función que sirve para eliminar un fichero.
     *
     * @param nombre Fichero a eliminar.
     */
    suspend fun deleteFile(nombre: String): Unit = withContext(Dispatchers.IO) {
        logger.debug { "Borrando fichero..." }
        val file = File("${storageConfig.uploadDir}/$nombre")
        logger.debug { "Fichero: $file" }

        if (!file.exists()) {
            throw Exception("Fichero $nombre no encontrado")
        } else {
            file.delete()
        }
    }
}