package es.ruymi.repositories

import es.ruymi.models.Departamento
import es.ruymi.repositories.departamento.DepartamentoRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.*
import kotlin.test.Test

class DepartamentoRepositoryImplTest {

    private val repository = DepartamentoRepositoryImpl()

    private val departamento = Departamento(
        UUID.fromString("7d737184-c159-49c1-a083-f42fecd12a53"),
        "Reparacion",
        270
    )
    @BeforeEach
    fun setUp(): Unit = runBlocking{
        repository.deleteAll()
        repository.insert(departamento)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val departTest = repository.findAll().toList()
        assertAll(
            {assertEquals(3, departTest.size)},
            {assertEquals(departamento.id, departTest[2].id)},
            {assertEquals(departamento.nombre, departTest[2].nombre)},
            {assertEquals(departamento.presupuesto, departTest[2].presupuesto)}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest{
        val departTest = repository.findById(UUID.fromString("7d737184-c159-49c1-a083-f42fecd12a53"))!!
        assertAll(
            {assertEquals(departamento.id, departTest.id)},
            {assertEquals(departamento.nombre, departTest.nombre)},
            {assertEquals(departamento.presupuesto, departTest.presupuesto)}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun insert() = runTest{
        repository.deleteAll()
        val departTest = repository.insert(departamento)
        assertAll(
            {assertEquals(departamento.id, departTest.id)},
            {assertEquals(departamento.nombre, departTest.nombre)},
            {assertEquals(departamento.presupuesto, departTest.presupuesto)}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest{
        departamento.nombre = "test"
        val departTest = repository.insert(departamento)
        assertAll(
            {assertEquals(departamento.id, departTest.id)},
            {assertEquals(departamento.nombre, departTest.nombre)},
            {assertEquals(departamento.presupuesto, departTest.presupuesto)}
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest{
        val departTest = repository.delete(departamento)
        assertAll(
            { assertTrue(departTest) }
        )
    }
}