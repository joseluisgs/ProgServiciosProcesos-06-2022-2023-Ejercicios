package com.example.services.users

import com.example.models.User
import com.example.repositories.usuarios.UserRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

import java.util.*


@Single
class UserServiceImpl(
    @Named("UsuarioRepository")
    private val repository: UserRepository
) : IUserService {
    override suspend fun findAll(): Flow<User> {
        return repository.findAll()
    }

    override suspend fun findById(id: UUID): User {
        return repository.findById(id) ?: throw Exception("No existe ese usuario")
    }

    override suspend fun login(username: String, password: String): User? {
        return repository.checkUsenamePassword(username, password)
    }

    override suspend fun save(entity: User): User {
        return repository.save(entity)
    }

    override suspend fun update(id: UUID, entity: User): User {
        val existe = repository.findById(id)

        existe?.let {
            return repository.update(entity)!!
        } ?: throw Exception("No se encontro ese usuario")
    }

    override suspend fun delete(entity: User): User {
        val existe = repository.findById(entity.id)

        existe?.let {
            return repository.delete(existe)!!
        } ?: throw Exception("No se encontro ese usuario")
    }
}