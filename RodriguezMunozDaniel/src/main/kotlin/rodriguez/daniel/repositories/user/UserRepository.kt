package rodriguez.daniel.repositories.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import rodriguez.daniel.model.User
import rodriguez.daniel.services.utils.matches
import java.util.*

@Single
class UserRepository : IUserRepository {
    private val db = mutableMapOf<UUID, User>()

    override suspend fun findAll(): Flow<User> = withContext(Dispatchers.IO) {
        db.values.asFlow()
    }

    override suspend fun findById(id: UUID): User? = withContext(Dispatchers.IO) {
        db[id]
    }

    override suspend fun findByEmail(email: String): User? = withContext(Dispatchers.IO) {
        db.values.toList().firstOrNull { it.email == email }
    }

    override suspend fun save(entity: User): User = withContext(Dispatchers.IO) {
        db[entity.id] = entity
        entity
    }

    override suspend fun delete(id: UUID): User? = withContext(Dispatchers.IO) {
        db.remove(id)
    }

    override suspend fun checkEmailAndPassword(email: String, password: String): User? = withContext(Dispatchers.IO) {
        val user = findByEmail(email)
        user?.let {
            if (matches(password, user.password.encodeToByteArray())) user
            else null
        }
    }
}