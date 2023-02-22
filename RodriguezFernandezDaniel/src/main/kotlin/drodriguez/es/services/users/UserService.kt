package drodriguez.es.services.users

import drodriguez.es.models.User
import drodriguez.es.repositories.user.UserRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
class UserService(
    @Named("UserRepository")
    private val userRepository: UserRepository,
): IUserService {
    override suspend fun findAll(): Flow<User> {
        return userRepository.findAll()
    }

    override suspend fun findById(id: UUID): User {
        return userRepository.findById(id)?: throw Exception("User not found")
    }

    override suspend fun login(username: String, password: String): User? {
        return userRepository.login(username, password)
    }

    override suspend fun save(entity: User): User {
        return userRepository.save(entity)
    }

    override suspend fun update(id: UUID, entity: User): User {
        val existe = userRepository.findById(id)
        existe?.let {
            return userRepository.update(id, entity)!!
        } ?: throw Exception("User not found")
    }

    override suspend fun delete(entity: User): User {
        val existe = userRepository.findById(entity.id)
        existe?.let {
            return userRepository.delete(existe)!!
        }?: throw Exception("User not found")
    }
}