package rodriguez.daniel.repositories.empleado

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import rodriguez.daniel.dto.EmpleadoDTOcreacion
import rodriguez.daniel.mappers.fromDTO
import rodriguez.daniel.model.Empleado
import rodriguez.daniel.services.cache.empleado.EmpleadoCache
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmpleadoRepositoryCachedTest {
    private val repo = EmpleadoRepository()
    private val cache = EmpleadoCache()
    private val repository = EmpleadoRepositoryCached(repo, cache)

    private val entity = EmpleadoDTOcreacion(
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea1707"),
        "Test", "empleadoTest@gmail.com", "",
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0005")
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
        var result: Empleado? = null
        launch { result = repository.save(entity) }.join()

        assertAll(
            { Assertions.assertEquals(result?.id, entity.id) },
            { Assertions.assertEquals(result?.nombre, entity.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        var result: List<Empleado>? = null
        launch { result = repository.findAll().toList() }.join()

        assertAll(
            { Assertions.assertNotNull(result) },
            { Assertions.assertEquals(entity.nombre, result?.get(0)?.nombre) },
            { Assertions.assertEquals(entity.id, result?.get(0)?.id) },
            { Assertions.assertEquals(1, result?.size) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        var result: Empleado? = null
        launch { result = repository.findById(entity.id) }.join()

        Assertions.assertAll(
            { Assertions.assertEquals(entity.id, result?.id) },
            { Assertions.assertEquals(entity.nombre, result?.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        var result: Empleado? = null
        launch { result = repository.delete(entity.id) }.join()

        assertAll(
            { Assertions.assertEquals(result?.nombre, entity.nombre) },
            { Assertions.assertEquals(result?.id, entity.id) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNotExists() = runTest {
        var result: Empleado? = null
        launch { result = repository.findById(UUID.randomUUID()) }.join()

        Assertions.assertNull(result)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNotExists() = runTest {
        var result: Empleado? = null
        launch { result = repository.delete(UUID.randomUUID()) }.join()

        Assertions.assertNull(result)
    }
}