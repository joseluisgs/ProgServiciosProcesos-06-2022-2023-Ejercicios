package resa.mario.repositories.departamento

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import resa.mario.db.Database
import resa.mario.models.Departamento
import resa.mario.services.DataBaseService
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DepartamentoRepositoryImplTest {

    private val database = Database()
    private val dataBaseService = DataBaseService(database)

    private var repository = DepartamentoRepositoryImpl(dataBaseService)

    private val departamento = Departamento(
        id = UUID.randomUUID(),
        nombre = "TEST",
        presupuesto = 100.0
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
        val res = repository.findById(UUID.fromString("1d89e51a-af79-11ed-afa1-0242ac120002"))

        assertEquals("Interfaces", res?.nombre)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = repository.save(departamento)

        assertEquals(res.nombre, departamento.nombre)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        val res = repository.save(departamento)
        val update = res.copy(nombre = "BBDD_TEST")
        val result = repository.update(departamento.id, update)

        assertEquals(result?.nombre, update.nombre)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = repository.save(departamento)
        val result = repository.delete(res)

        assertEquals(result?.nombre, res.nombre)
    }
}