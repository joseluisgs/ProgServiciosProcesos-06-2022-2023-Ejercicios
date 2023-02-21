package resa.mario.services.departamento

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
import resa.mario.models.Departamento
import resa.mario.repositories.departamento.DepartamentoCachedRepositoryImpl
import resa.mario.repositories.empleado.EmpleadoCachedRepositoryImpl
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class DepartamentoServiceImplTest {

    private val departamento = Departamento(
        nombre = "TEST",
        presupuesto = 100.0
    )

    @MockK
    private lateinit var repository: DepartamentoCachedRepositoryImpl

    @MockK
    lateinit var repository2: EmpleadoCachedRepositoryImpl

    @InjectMockKs
    lateinit var service: DepartamentoServiceImpl

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
        coEvery { repository.findById(any()) } returns departamento

        val res = service.findById(departamento.id)

        assertEquals(departamento.nombre, res.nombre)

        coVerify { repository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { repository.save(any()) } returns departamento

        val res = service.save(departamento)

        assertEquals(departamento.nombre, res.nombre)

        coVerify { repository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { repository.update(any(), any()) } returns departamento

        val res = service.update(departamento.id, departamento)

        assertEquals(departamento.nombre, res.nombre)

        coVerify { repository.update(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { repository.findById(any()) } returns departamento
        coEvery { repository.delete(any()) } returns departamento
        coEvery { repository2.findAll() } returns flowOf()

        val res = service.delete(departamento)

        assertEquals(departamento.nombre, res.nombre)

        coVerify { repository.delete(any()) }
    }
}