package com.example.repositories

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
import com.example.models.Departamento
import com.example.repositories.departamentos.DepartamentoCacheRepositoryImpl
import com.example.repositories.departamentos.DepartamentoRepositoryImpl
import com.example.services.cache.DepartamentoCache
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class DepartamentoCachedRepositoryImplTest {

    val depart = Departamento(
        id = UUID.randomUUID(),
        name = "Informatica",
        presupuesto = 30.0
    )

    @MockK
    lateinit var departamentoRepositoryImpl: DepartamentoRepositoryImpl

    @SpyK
    var cache = DepartamentoCache()

    @InjectMockKs
    lateinit var departamentoCacheRepositoryImpl: DepartamentoCacheRepositoryImpl

    init {
        MockKAnnotations.init(this)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        coEvery { departamentoRepositoryImpl.save(any()) } returns depart
        val res = departamentoCacheRepositoryImpl.save(depart)
        assertEquals(depart.name, res.name)
        coVerify { departamentoRepositoryImpl.save(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        coEvery { departamentoRepositoryImpl.update(any()) } returns depart
        val res = departamentoCacheRepositoryImpl.update( depart)
        assertEquals(depart.name, res.name)
        coVerify { departamentoRepositoryImpl.update(any()) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        coEvery { departamentoRepositoryImpl.findById(any()) } returns depart
        coEvery { departamentoRepositoryImpl.delete(any()) } returns depart
        val res = departamentoCacheRepositoryImpl.delete(depart)
        assertEquals(depart.name, res?.name)
        coVerify { departamentoRepositoryImpl.delete(depart) }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        coEvery { departamentoRepositoryImpl.findAll() } returns flowOf(depart)
        val res = departamentoCacheRepositoryImpl.findAll().toList()
        assertTrue(res.isNotEmpty())
        coVerify { departamentoRepositoryImpl.findAll() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        coEvery { departamentoRepositoryImpl.findById(any()) } returns depart
        val res = departamentoCacheRepositoryImpl.findById(depart.id)
        assertEquals(depart.name, res!!.name)
        coVerify { departamentoRepositoryImpl.findById(any()) }
    }
}