package com.example.services

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import com.example.models.Empleado
import com.example.repositories.empleados.EmpleadosRepository
import com.example.services.empleado.EmpleadoService

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class EmpleadoServiceImplTest {

    private val empleado = Empleado(
        name = "Dani",
        email = "dani@dani.com",
        departamentoId = null,
        avatar = "dani",

    )

    @MockK
    private lateinit var repository: EmpleadosRepository

    @InjectMockKs
    lateinit var service: EmpleadoService

    init {
        MockKAnnotations.init(this)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { repository.findById(any()) } returns empleado
        val resultado = repository.findById(empleado.id)
        assertEquals(empleado, resultado)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { repository.save(any()) } returns empleado
        val resultado = repository.save(empleado)
        assertEquals(empleado, resultado)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { repository.update(any()) } returns empleado
        val resultado = repository.update(empleado)
        assertEquals(empleado, resultado)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { repository.delete(any()) } returns empleado
        val resultado = repository.delete(empleado)
        assertEquals(empleado, resultado)
    }
}