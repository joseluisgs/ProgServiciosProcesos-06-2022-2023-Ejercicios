package com.example.repositories

import com.example.models.User
import com.example.repositories.usuarios.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mindrot.jbcrypt.BCrypt

import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsuarioRepositoryImplTest {

    private var repository = UserRepository()

    private val usuario = User(
        id = UUID.randomUUID(),
        username = "Pepe",
        password = BCrypt.hashpw("1234", BCrypt.gensalt(12)),
        type = User.Type.ADMIN.name
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login() = runTest {
        repository.save(usuario)
        val res = repository.checkUsenamePassword(usuario.username, "1234")
        assertEquals(usuario.username, res?.username)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = repository.findAll().toList()
        assertNotNull(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        repository.save(usuario)
        val res = repository.findById(UUID.fromString(usuario.id.toString()))
        assertEquals(usuario.username, res?.username)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = repository.save(usuario)
        assertEquals(res.username, usuario.username)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        val res = repository.save(usuario)
        val update = res.copy(username = "Pepe")
        val result = repository.update(update)
        assertEquals(result?.username, update.username)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = repository.save(usuario)
        val result = repository.delete(res)
        assertEquals(result?.username, res.username)
    }
}