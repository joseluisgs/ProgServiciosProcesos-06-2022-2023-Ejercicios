package com.example.services.storage

import io.ktor.utils.io.*
import java.io.File

interface Storage {
    fun initConfiguration()
    suspend fun saveFile(name: String, channel: ByteReadChannel): Map<String,String>
    fun getFile(name: String): File?

}
