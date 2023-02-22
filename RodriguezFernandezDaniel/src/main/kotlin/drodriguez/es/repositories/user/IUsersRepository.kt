package drodriguez.es.repositories.user

import drodriguez.es.models.User
import drodriguez.es.repositories.CrudRepository
import java.util.*

interface IUsersRepository: CrudRepository<User, UUID> {
    suspend fun findByUsername(username: String): User?
    suspend fun login(username: String, password: String): User?
}