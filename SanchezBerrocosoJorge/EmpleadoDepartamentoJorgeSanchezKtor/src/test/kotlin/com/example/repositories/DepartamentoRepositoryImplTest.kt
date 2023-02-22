package com.example.repositories

import com.example.models.Departamento
import com.example.repositories.departamentos.DepartamentoRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance

import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartamentoRepositoryImplTest {


    private var repository = DepartamentoRepositoryImpl()

    private val departamento = Departamento(
        id = UUID.randomUUID(),
        name = "PEPE",
        presupuesto = 30.0
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = repository.save(departamento)
        assertEquals(res.name, departamento.name)
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
        val res = repository.findById(departamento.id)
        assertEquals("PEPE", departamento?.name)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = repository.save(departamento)
        val result = repository.delete(res)
        assertEquals(result?.name, res.name)
    }
}