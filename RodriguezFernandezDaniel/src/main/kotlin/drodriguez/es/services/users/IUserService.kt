package drodriguez.es.services.users

import drodriguez.es.models.User
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IUserService {
    suspend fun findAll(): Flow<User>
    suspend fun findById(id: UUID): User
    suspend fun login(username: String, password: String): User?
    suspend fun save(entity: User): User
    suspend fun update(id: UUID, entity: User): User
    suspend fun delete(entity: User): User
}