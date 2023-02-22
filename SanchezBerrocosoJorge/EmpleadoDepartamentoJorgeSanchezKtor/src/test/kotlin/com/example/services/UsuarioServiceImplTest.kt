package com.example.services

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import com.example.models.User
import com.example.repositories.usuarios.UserRepository
import com.example.services.users.UserServiceImpl
import org.mindrot.jbcrypt.BCrypt
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class UsuarioServiceImplTest {

    private val usuario = User(
        username = "Pepe",
        password = BCrypt.hashpw("1234", BCrypt.gensalt(12)),
        type = User.Type.ADMIN.name
    )

    @MockK
    private lateinit var userRepository: UserRepository

    @InjectMockKs
    private lateinit var userServiceImpl: UserServiceImpl

    init {
        MockKAnnotations.init(this)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { userRepository.findById(any()) } returns usuario
        val res = userServiceImpl.findById(usuario.id)
        assertEquals(usuario.username, res.username)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login() = runTest {
        coEvery { userRepository.checkUsenamePassword(any(), any()) } returns usuario
        val res = userServiceImpl.login(usuario.username, "1234")
        assertEquals(usuario.username, res?.username)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { userRepository.save(any()) } returns usuario
        val res = userServiceImpl.save(usuario)
        assertEquals(usuario.username, res.username)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { userRepository.update(any()) } returns usuario
        val res = userServiceImpl.update(usuario.id, usuario)
        assertEquals(usuario.username, res.username)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { userRepository.findById(any()) } returns usuario
        coEvery { userRepository.delete(any()) } returns usuario
        val res = userServiceImpl.delete(usuario)
        assertEquals(usuario.username, res.username)

    }
}