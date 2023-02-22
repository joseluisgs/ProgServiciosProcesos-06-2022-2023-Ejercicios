package drodriguez.es.repositories.user

import com.toxicbakery.bcrypt.Bcrypt
import drodriguez.es.db.DataBase
import drodriguez.es.models.User
import drodriguez.es.services.DBServices
import io.ktor.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("UserRepository")
class UserRepository(
    private val dbService: DBServices
): IUsersRepository {
    override suspend fun findByUsername(username: String): User? {
        var user: User? = null
        dbService.getLists().listUsers.values.forEach {
            if (it.username == username) {
                user = it
            }
        }
        return user
    }

    override suspend fun login(username: String, password: String): User? {
        val user = findByUsername(username) ?: return null
        user.let {
            if (Bcrypt.verify(password, user.password.encodeToByteArray())) {
                return user
            } else return null
        }
    }

    override suspend fun findAll(): Flow<User> {
        return dbService.getLists().listUsers.values.asFlow()
    }

    override suspend fun findById(id: UUID): User? {
        return dbService.getLists().listUsers.values.firstOrNull { it.id == id }
    }

    override suspend fun save(entity: User): User {
        dbService.getLists().listUsers[entity.id] = entity
        return dbService.getLists().listUsers[entity.id]!!
    }

    override suspend fun update(id: UUID, entity: User): User? {
        val user = findById(id)?: return null
        val userCC = entity.copy(
            id = user.id,
            username = entity.username,
            password = entity.password,
            rol = entity.rol
        )
        user.let {
            dbService.getLists().listUsers.replace(id, userCC)
        }
        return userCC
    }

    override suspend fun delete(entity: User): User? {
        val user = findById(entity.id)?: return null
        dbService.getLists().listUsers.remove(entity.id)
        return user
    }
}