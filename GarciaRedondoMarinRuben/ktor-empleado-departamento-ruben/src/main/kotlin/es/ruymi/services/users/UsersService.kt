package es.ruymi.services.users

import es.ruymi.models.User
import kotlinx.coroutines.flow.Flow
import java.util.*

interface UsersService {
    suspend fun findAll(): Flow<User>
    suspend fun findById(id: UUID): User
    suspend fun findByUsername(username: String): User?
    fun hashedPassword(password: String): String
    suspend fun checkUserNameAndPassword(username: String, password: String): User?
    suspend fun save(entity: User): User
    suspend fun update(id: UUID, entity: User): User?
    suspend fun delete(id: UUID): User?
}