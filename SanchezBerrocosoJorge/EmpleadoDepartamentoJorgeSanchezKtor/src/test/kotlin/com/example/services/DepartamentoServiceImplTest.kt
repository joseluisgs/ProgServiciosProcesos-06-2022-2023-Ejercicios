package com.example.services

import com.example.models.Departamento
import com.example.repositories.departamentos.DepartamentoCacheRepositoryImpl
import com.example.repositories.departamentos.DepartamentoRepositoryImpl
import com.example.services.departamento.DepartamentoService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class DepartamentoServiceImplTest {

    private val departamento = Departamento(
        name = "Juan",
        presupuesto = 30.0
    )

    @MockK
    private lateinit var departamentoCacheRepositoryImpl: DepartamentoCacheRepositoryImpl

    @MockK
    lateinit var departamentoRepositoryImpl: DepartamentoRepositoryImpl

    @InjectMockKs
    lateinit var departamentoService: DepartamentoService

    init {
        MockKAnnotations.init(this)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { departamentoRepositoryImpl.save(any()) } returns departamento
        val resultado = departamentoService.save(departamento)
        assertEquals(departamento, resultado)
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { departamentoRepositoryImpl.findById(any()) } returns departamento
        val resultado = departamentoService.findById(departamento.id)
        assertEquals(departamento, resultado)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {

        coEvery { departamentoRepositoryImpl.update(any()) } returns departamento
        val resultado = departamentoService.update(departamento)
        assertEquals(departamento, resultado)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {

        coEvery { departamentoRepositoryImpl.delete(any()) } returns departamento
        val resultado = departamentoService.delete(departamento)
        assertEquals(departamento, resultado)
    }
}