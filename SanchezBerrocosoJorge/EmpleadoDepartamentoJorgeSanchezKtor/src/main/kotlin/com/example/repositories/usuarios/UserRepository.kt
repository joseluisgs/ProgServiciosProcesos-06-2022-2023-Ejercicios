package com.example.repositories.usuarios

import com.example.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.mindrot.jbcrypt.BCrypt
import java.util.*

@Single
@Named("UsuarioRepository")
class UserRepository : IUsersRepository {

    val list = mutableListOf<User>()

    override suspend fun hashedPassword(password: String): String {
        val password = BCrypt.hashpw(password, BCrypt.gensalt(12))
        return password
    }

    override suspend fun checkUsenamePassword(username: String, password: String): User? = withContext(Dispatchers.IO) {
        val user = list.find { it.username == username }
        if(user!= null) {
            if(BCrypt.checkpw(password, user.password)){
                return@withContext user
            }
        }
        return@withContext null

    }

    override suspend fun findAll(): Flow<User> {
        return list.asFlow()
    }

    override suspend fun findById(id: UUID): User? {
        val item = list.find { it.id == id }
        return item
    }

    override suspend fun delete(item: User): User?  = withContext(Dispatchers.IO) {
        list.remove(item)
        return@withContext item
    }

    override suspend fun save(item: User): User  = withContext(Dispatchers.IO) {
        list.add(item)
        return@withContext item
    }

    override suspend fun update(item: User): User  = withContext(Dispatchers.IO) {
        val user = list.find { it.id == item.id }
        list.remove(user)
        list.add(item)
        return@withContext item
    }

}