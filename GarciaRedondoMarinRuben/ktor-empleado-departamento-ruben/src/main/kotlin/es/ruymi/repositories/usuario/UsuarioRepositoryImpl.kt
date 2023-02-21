package es.ruymi.repositories.usuario

import es.ruymi.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.mindrot.jbcrypt.BCrypt
import java.util.*

@Single
class UsuarioRepositoryImpl: UsuarioRepository {
    private val db : MutableMap<UUID, User> = mutableMapOf()
    override suspend fun findAll(): Flow<User> {
        return db.values.toList().asFlow()
    }

    override suspend fun findById(id: UUID): User? = withContext(Dispatchers.IO) {
        return@withContext db[id]
    }
    override fun hashedPassword(password: String) = BCrypt.hashpw(password, BCrypt.gensalt(12))


    override suspend fun checkUserNameAndPassword(username: String, password: String): User? = withContext(Dispatchers.IO) {
        val user = findByUsername(username)
        return@withContext user?.let {
            if (BCrypt.checkpw(password, user.password)) {
                return@withContext user
            }
            return@withContext null
        }
    }
    override suspend fun findByUsername(username: String): User? = withContext(Dispatchers.IO) {
        return@withContext db.values.firstOrNull { it.usuario == username }
    }


    override suspend fun insert(entity: User): User = withContext(Dispatchers.IO) {
        db[entity.id] = entity
        return@withContext db[entity.id]!!
    }

    override suspend fun update(entity: User): User? = withContext(Dispatchers.IO) {
        db[entity.id]?.let {
            db.replace(entity.id, entity)
            return@let entity
        }
        return@withContext null
    }

    override suspend fun delete(entity: User): Boolean = withContext(Dispatchers.IO) {
        db.remove(entity.id)?.let {
            return@let true
        }
        return@withContext false
    }
}