package rodriguez.daniel.repositories.user

import rodriguez.daniel.model.User
import rodriguez.daniel.repositories.ICRUDRepository
import java.util.*

interface IUserRepository : ICRUDRepository<User, UUID> {
    suspend fun findByEmail(email: String): User?
    suspend fun checkEmailAndPassword(email: String, password: String): User?
}