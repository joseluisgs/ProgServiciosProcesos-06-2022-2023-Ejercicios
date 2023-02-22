package com.example.repositories.usuarios

import com.example.models.User
import com.example.repositories.CrudRepository
import java.util.UUID

interface IUsersRepository : CrudRepository<UUID,User> {
    suspend fun hashedPassword(password: String) : String
    suspend fun checkUsenamePassword(username: String, password: String) : User?
}