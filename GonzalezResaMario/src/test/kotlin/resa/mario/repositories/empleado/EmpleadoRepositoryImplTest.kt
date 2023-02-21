package resa.mario.repositories.empleado

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import resa.mario.db.Database
import resa.mario.models.Empleado
import resa.mario.services.DataBaseService
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EmpleadoRepositoryImplTest {

    private val database = Database()
    private val dataBaseService = DataBaseService(database)

    private var repository = EmpleadoRepositoryImpl(dataBaseService)

    private val empleado = Empleado(
        nombre = "Test",
        email = "test@test.com",
        avatar = "test"
    )

    @BeforeEach
    fun setUp() {
        dataBaseService.clearDataBaseService()
        dataBaseService.initDataBaseService()
    }

    @AfterAll
    fun tearDown() {
        dataBaseService.clearDataBaseService()
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
        val res = repository.findById(UUID.fromString("db7fbce4-b095-11ed-afa1-0242ac120002"))

        assertEquals("Mario", res?.nombre)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = repository.save(empleado)

        assertEquals(res.nombre, empleado.nombre)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        val res = repository.save(empleado)
        val update = res.copy(nombre = "TEST_2")
        val result = repository.update(empleado.id, update)

        assertEquals(result?.nombre, update.nombre)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = repository.save(empleado)
        val result = repository.delete(res)

        assertEquals(result?.nombre, res.nombre)
    }
}