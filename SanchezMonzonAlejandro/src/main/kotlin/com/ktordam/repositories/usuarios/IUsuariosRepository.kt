package com.ktordam.repositories.usuarios

import com.ktordam.models.Usuario
import com.ktordam.repositories.CRUDRepository
import java.util.UUID

interface IUsuariosRepository: CRUDRepository<Usuario, UUID> {
    suspend fun findByUserame(username: String): Usuario?
    suspend fun login(username: String, password: String): Usuario?
}