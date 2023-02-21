package rodriguez.daniel.services.user

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import rodriguez.daniel.db.departamentos
import rodriguez.daniel.db.users
import rodriguez.daniel.dto.*
import rodriguez.daniel.exception.UserUnauthorizedException
import rodriguez.daniel.mappers.fromDTO
import rodriguez.daniel.mappers.toDTO
import rodriguez.daniel.model.User
import rodriguez.daniel.repositories.user.IUserRepository
import java.util.*

@Single
class UserService(private val repo: IUserRepository) {
    suspend fun findUserById(id: UUID): UserDTO? = withContext(Dispatchers.IO) {
        repo.findById(id)?.toDTO()
    }

    suspend fun findAllUsers(): List<UserDTO> = withContext(Dispatchers.IO) {
        val entities = repo.findAll().toList()
        val response = mutableListOf<UserDTO>()
        entities.forEach { response.add(it.toDTO()) }

        response
    }

    suspend fun saveUser(entity: UserDTOcreacion): UserDTO = withContext(Dispatchers.IO) {
        repo.save(entity.fromDTO()).toDTO()
    }

    suspend fun deleteUser(id: UUID): UserDTO? = withContext(Dispatchers.IO) {
        repo.delete(id)?.toDTO()
    }

    suspend fun checkEmailAndPassword(email: String, password: String): User = withContext(Dispatchers.IO) {
        repo.checkEmailAndPassword(email, password)
            ?: throw UserUnauthorizedException("Incorrect email or password.")
    }
}