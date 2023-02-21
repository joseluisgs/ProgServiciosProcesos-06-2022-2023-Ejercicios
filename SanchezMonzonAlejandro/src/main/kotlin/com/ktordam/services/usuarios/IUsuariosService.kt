package com.ktordam.services.usuarios

import com.ktordam.models.Usuario
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IUsuariosService {
    suspend fun findAll(): Flow<Usuario>
    suspend fun findByUUID(uuid: UUID): Usuario
    suspend fun findByUserame(username: String): Usuario
    suspend fun login(username: String, password: String): Usuario?
    suspend fun save(usuario: Usuario): Usuario
    suspend fun update(uuid: UUID, usuario: Usuario): Usuario
    suspend fun delete(uuid: UUID): Usuario?
}