package resa.mario.repositories.usuario

import resa.mario.models.Usuario
import resa.mario.repositories.CrudRepository
import java.util.UUID

interface UsuarioRepository : CrudRepository<Usuario, UUID> {
    suspend fun findByName(username: String): Usuario?
    suspend fun login(username: String, password: String): Usuario?
}