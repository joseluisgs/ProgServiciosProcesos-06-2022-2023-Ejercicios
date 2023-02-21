package resa.mario.repositories.departamento

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
import resa.mario.models.Departamento
import resa.mario.services.cache.DepartamentoCache
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class DepartamentoCachedRepositoryImplTest {

    private val departamento = Departamento(
        id = UUID.randomUUID(),
        nombre = "TEST",
        presupuesto = 100.0
    )

    @MockK
    lateinit var rep: DepartamentoRepositoryImpl

    @SpyK
    var cache = DepartamentoCache()

    @InjectMockKs
    lateinit var repository: DepartamentoCachedRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { rep.findAll() } returns flowOf(departamento)

        val res = repository.findAll().toList()

        assertTrue(res.isNotEmpty())

        coVerify { rep.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { rep.findById(any()) } returns departamento

        val res = repository.findById(departamento.id)

        assertEquals(departamento.nombre, res!!.nombre)

        coVerify { rep.findById(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { rep.save(any()) } returns departamento

        val res = repository.save(departamento)

        assertEquals(departamento.nombre, res.nombre)

        coVerify { rep.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { rep.update(any(), any()) } returns departamento

        val res = repository.update(departamento.id, departamento)

        assertEquals(departamento.nombre, res.nombre)

        coVerify { rep.update(any(), any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { rep.findById(any()) } returns departamento
        coEvery { rep.delete(any()) } returns departamento

        val res = repository.delete(departamento)

        assertEquals(departamento.nombre, res?.nombre)

        coVerify { rep.delete(departamento) }
    }
}