package com.example.services.storage

import com.example.config.StorageConfig
import java.io.File

interface StorageService {
    fun getConfig(): StorageConfig
    fun initStorageDirectory()
    suspend fun saveFile(fileName: String, fileBytes: ByteArray): Map<String, String>
    suspend fun getFile(fileName: String): File
    suspend fun deleteFile(fileName: String)
}