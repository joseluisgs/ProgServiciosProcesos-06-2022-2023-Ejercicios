package mendoza.js.repositories.users

import kotlinx.coroutines.flow.Flow
import mendoza.js.models.User
import mendoza.js.repositories.CrudRepository
import java.util.*

interface UsersRepository : CrudRepository<User, UUID> {
    suspend fun findAll(limit: Int?): Flow<User>
    suspend fun findByUsername(username: String): User?
    fun hashedPassword(password: String): String
    suspend fun checkUserNameAndPassword(username: String, password: String): User?
}

