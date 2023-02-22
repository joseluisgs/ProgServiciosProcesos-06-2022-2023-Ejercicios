package es.ruymi.repositories.usuario

import es.ruymi.models.User
import es.ruymi.repositories.CrudRepository
import java.util.UUID

interface UsuarioRepository: CrudRepository<User, UUID> {
    suspend fun findByUsername(username: String): User?
    fun hashedPassword(password: String): String
    suspend fun checkUserNameAndPassword(username: String, password: String): User?
}