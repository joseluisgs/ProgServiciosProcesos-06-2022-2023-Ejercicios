package resa.mario.services.usuario

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import resa.mario.models.Usuario
import resa.mario.repositories.usuario.UsuarioRepositoryImpl
import resa.mario.utils.Cifrador
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class UsuarioServiceImplTest {

    private val usuario = Usuario(
        username = "TEST_USER",
        password = Cifrador.cipher("1234"),
        role = Usuario.Role.ADMIN.name
    )

    @MockK
    private lateinit var repository: UsuarioRepositoryImpl

    @InjectMockKs
    private lateinit var service: UsuarioServiceImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(usuario)

        val res = service.findAll().toList()

        assertTrue(res.isNotEmpty())

        coVerify { repository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { repository.findById(any()) } returns usuario

        val res = service.findById(usuario.id)

        assertEquals(usuario.username, res.username)

        coVerify { repository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login() = runTest {
        coEvery { repository.login(any(), any()) } returns usuario

        val res = service.login(usuario.username, "1234")

        assertEquals(usuario.username, res?.username)

        coVerify { repository.login(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { repository.save(any()) } returns usuario

        val res = service.save(usuario)

        assertEquals(usuario.username, res.username)

        coVerify { repository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { repository.update(any(), any()) } returns usuario

        val res = service.update(usuario.id, usuario)

        assertEquals(usuario.username, res.username)

        coVerify { repository.update(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { repository.findById(any()) } returns usuario
        coEvery { repository.delete(any()) } returns usuario

        val res = service.delete(usuario)

        assertEquals(usuario.username, res.username)

        coVerify { repository.delete(any()) }
    }
}