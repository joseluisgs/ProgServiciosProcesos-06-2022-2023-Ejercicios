package com.example.repositories

import com.example.models.Empleado
import com.example.repositories.empleados.EmpleadosRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmpleadoRepositoryImplTest {


    private var repository = EmpleadosRepository()

    private val empleado = Empleado(
        name = "Pepe",
        email = "pepe@pepe.com",
        departamentoId = null,
        avatar = "pepinos"
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = repository.save(empleado)
        assertEquals(res.name, empleado.name)
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
        val res = repository.findById(empleado.id)
        assertEquals("Pepe", empleado?.name)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = repository.save(empleado)
        val result = repository.delete(res)
        assertEquals(result?.name, res.name)
    }
}