package resa.mario.services.empleado

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
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import resa.mario.models.Empleado
import resa.mario.repositories.departamento.DepartamentoCachedRepositoryImpl
import resa.mario.repositories.empleado.EmpleadoCachedRepositoryImpl

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class EmpleadoServiceImplTest {

    private val empleado = Empleado(
        nombre = "Test",
        email = "test@test.com",
        avatar = "test"
    )

    @MockK
    private lateinit var repository: EmpleadoCachedRepositoryImpl

    @MockK
    private lateinit var repository2: DepartamentoCachedRepositoryImpl

    @InjectMockKs
    lateinit var service: EmpleadoServiceImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { repository.findAll() } returns flowOf(empleado)

        val res = service.findAll().toList()

        assertTrue(res.isNotEmpty())

        coVerify { repository.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { repository.findById(any()) } returns empleado

        val res = service.findById(empleado.id)

        assertEquals(empleado.nombre, res.nombre)

        coVerify { repository.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { repository.save(any()) } returns empleado

        val res = service.save(empleado)

        assertEquals(empleado.nombre, res.nombre)

        coVerify { repository.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { repository.update(any(), any()) } returns empleado

        val res = service.update(empleado.id, empleado)

        assertEquals(empleado.nombre, res.nombre)

        coVerify { repository.update(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { repository.findById(any()) } returns empleado
        coEvery { repository.delete(any()) } returns empleado

        val res = service.delete(empleado)

        assertEquals(empleado.nombre, res.nombre)

        coVerify { repository.delete(any()) }
    }
}