package sanchez.mireya.services.departamento

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
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import sanchez.mireya.models.Departamento
import sanchez.mireya.repositories.departamento.DepartamentosRepository
import sanchez.mireya.repositories.empleado.EmpleadosRepository
import sanchez.mireya.services.CacheService
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class DepartamentosServiceImplTest {

    private val departamento = Departamento(
        id = 10,
        name = "Test",
        budget = 0.0f
    )

    @MockK
    private lateinit var repository: DepartamentosRepository

    @MockK
    lateinit var repositoryEmpleados: EmpleadosRepository

    @MockK
    lateinit var cache: CacheService


    @InjectMockKs
    lateinit var service: DepartamentosServiceImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(departamento)

        val res = service.findAll().toList()

        assertTrue(res.isNotEmpty())

        coVerify { repository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { repository.findById(departamento.id) } returns departamento
        coEvery { cache.cache.get(departamento.id) } returns departamento

        val res = service.findById(departamento.id)

        assertEquals(departamento.name, res.name)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { repository.save(any()) } returns departamento
        coEvery { cache.cache.get(departamento.id) } returns departamento
        coEvery { cache.cache.put(departamento.id, departamento) } returns Unit

        val res = service.save(departamento)

        assertEquals(departamento.name, res.name)

        coVerify { repository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { repository.update(departamento.id, departamento) } returns departamento
        coEvery { repository.findById(departamento.id) } returns departamento
        coEvery { cache.cache.get(departamento.id) } returns departamento
        coEvery { cache.cache.put(departamento.id, departamento) } returns Unit

        val res = service.update(departamento.id, departamento)

        assertEquals(departamento.name, res.name)

        coVerify { repository.update(departamento.id, departamento) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { repository.findAll() } returns flowOf(departamento)
        coEvery { repositoryEmpleados.findAll() } returns flowOf()
        coEvery { repository.findById(departamento.id) } returns departamento
        coEvery { repository.delete(departamento.id) } returns departamento

        val res = service.delete(departamento.id)

        assertEquals(departamento.name, res.name)

        coVerify { repository.delete(departamento.id) }
    }
}