package resa.mario.repositories.usuario

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import resa.mario.db.Database
import resa.mario.models.Usuario
import resa.mario.services.DataBaseService
import resa.mario.utils.Cifrador
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UsuarioRepositoryImplTest {

    private val database = Database()
    private val dataBaseService = DataBaseService(database)

    private var repository = UsuarioRepositoryImpl(dataBaseService)

    private val usuario = Usuario(
        id = UUID.fromString("d390c1b4-b12a-11ed-afa1-0242ac120002"),
        username = "TEST_USER",
        password = Cifrador.cipher("1234"),
        role = Usuario.Role.ADMIN.name
    )

    @BeforeEach
    fun setUp() {
        dataBaseService.clearDataBaseService()
        dataBaseService.initDataBaseService()
    }

    @AfterEach
    fun tearDown() {
        dataBaseService.clearDataBaseService()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByName() = runTest {
        repository.save(usuario)

        val res = repository.findByName(usuario.username)

        assertEquals(usuario.username, res?.username)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login() = runTest {
        repository.save(usuario)
        val res = repository.login(usuario.username, "1234")

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
        val update = res.copy(username = "USER_TEST_2")
        val result = repository.update(usuario.id, update)

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