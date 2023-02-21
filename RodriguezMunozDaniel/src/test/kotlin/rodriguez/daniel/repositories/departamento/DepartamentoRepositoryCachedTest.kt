package rodriguez.daniel.repositories.departamento

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import rodriguez.daniel.dto.DepartamentoDTOcreacion
import rodriguez.daniel.mappers.fromDTO
import rodriguez.daniel.model.Departamento
import rodriguez.daniel.services.cache.departamento.DepartamentoCache
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartamentoRepositoryCachedTest {
    private val repo = DepartamentoRepository()
    private val cache = DepartamentoCache()
    private val repository = DepartamentoRepositoryCached(repo, cache)

    private val entity = DepartamentoDTOcreacion(
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0005"),
        "Departamento test", 20.69
    ).fromDTO()

    @BeforeEach
    fun data() = runBlocking {
        val x = repository.save(entity)
    }

    @AfterAll
    fun tearDown() = runBlocking {
        val x = repository.findAll().toList().forEach { repository.delete(it.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        var result: Departamento? = null
        launch { result = repository.save(entity) }.join()

        assertAll(
            { assertEquals(result?.id, entity.id) },
            { assertEquals(result?.nombre, entity.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        var result: List<Departamento>? = null
        launch { result = repository.findAll().toList() }.join()

        assertAll(
            { Assertions.assertNotNull(result) },
            { assertEquals(entity.nombre, result?.get(0)?.nombre) },
            { assertEquals(entity.id, result?.get(0)?.id) },
            { assertEquals(1, result?.size) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        var result: Departamento? = null
        launch { result = repository.findById(entity.id) }.join()

        Assertions.assertAll(
            { assertEquals(entity.id, result?.id) },
            { assertEquals(entity.nombre, result?.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        var result: Departamento? = null
        launch { result = repository.delete(entity.id) }.join()

        assertAll(
            { assertEquals(result?.nombre, entity.nombre) },
            { assertEquals(result?.id, entity.id) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        var result: Departamento? = null
        launch { result = repository.findById(UUID.randomUUID()) }.join()

        Assertions.assertNull(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNotExists() = runTest {
        var result: Departamento? = null
        launch { result = repository.delete(UUID.randomUUID()) }.join()

        Assertions.assertNull(result)
    }
}