package resa.mario.repositories.empleado

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
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
import resa.mario.services.cache.EmpleadoCache

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class EmpleadoCachedRepositoryImplTest {

    private val empleado = Empleado(
        nombre = "Test",
        email = "test@test.com",
        avatar = "test"
    )

    @MockK
    private lateinit var rep: EmpleadoRepositoryImpl

    @SpyK
    private var cache = EmpleadoCache()

    @InjectMockKs
    private lateinit var repository: EmpleadoCachedRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { rep.findAll() } returns flowOf(empleado)

        val res = repository.findAll().toList()

        assertTrue(res.isNotEmpty())

        coVerify { rep.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { rep.findById(any()) } returns empleado

        val res = repository.findById(empleado.id)

        assertEquals(empleado.nombre, res!!.nombre)

        coVerify { rep.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { rep.save(any()) } returns empleado

        val res = repository.save(empleado)

        assertEquals(empleado.nombre, res.nombre)

        coVerify { rep.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { rep.update(any(), any()) } returns empleado

        val res = repository.update(empleado.id, empleado)

        assertEquals(empleado.nombre, res.nombre)

        coVerify { rep.update(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { rep.findById(any()) } returns empleado
        coEvery { rep.delete(any()) } returns empleado

        val res = repository.delete(empleado)

        assertEquals(empleado.nombre, res?.nombre)

        coVerify { rep.delete(empleado) }
    }
}