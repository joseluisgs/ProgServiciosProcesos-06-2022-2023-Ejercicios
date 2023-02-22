package sanchez.mireya.repositories.departamento

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import sanchez.mireya.models.Departamento
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DepartamentosRepositoryTest {

    private var repository = DepartamentosRepository()

    private val departamento = Departamento(
        id = 10,
        budget = 0.0f,
        name = "Test"
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val res = repository.findAll().toList()

        assertNotNull(res)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        repository.save(departamento)
        val res = repository.findById(10)

        assertEquals(departamento.name, res?.name)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val res = repository.save(departamento)

        assertEquals(res.name, departamento.name)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun update() = runTest {
        repository.save(departamento)
        val update = Departamento(
            id = 10,
            budget = 0.0f,
            name = "Actualizacion"
        )
        val result = repository.update(departamento.id, update)

        assertEquals(result.name, update.name)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val res = repository.save(departamento)
        val result = repository.delete(10)

        assertEquals(result?.name, res.name)
    }
}