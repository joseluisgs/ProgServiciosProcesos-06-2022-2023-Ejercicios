package rodriguez.daniel.services.empleado

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import rodriguez.daniel.dto.DepartamentoDTOcreacion
import rodriguez.daniel.dto.EmpleadoDTOcreacion
import rodriguez.daniel.exception.EmpleadoExceptionBadRequest
import rodriguez.daniel.exception.EmpleadoExceptionNotFound
import rodriguez.daniel.mappers.fromDTO
import rodriguez.daniel.repositories.departamento.DepartamentoRepository
import rodriguez.daniel.repositories.departamento.DepartamentoRepositoryCached
import rodriguez.daniel.repositories.empleado.EmpleadoRepository
import rodriguez.daniel.repositories.empleado.EmpleadoRepositoryCached
import rodriguez.daniel.services.cache.departamento.DepartamentoCache
import rodriguez.daniel.services.cache.empleado.EmpleadoCache
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmpleadoServiceTest {
    val entity = EmpleadoDTOcreacion(
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0001"),
        "TEST", "empleado1@gmail.com", "",
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0003")
    )
    val departamento = DepartamentoDTOcreacion(
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0003"),
        "Departamento test", 69_420.69
    )

    private val repo = EmpleadoRepository()
    private val cache = EmpleadoCache()
    private val repository = EmpleadoRepositoryCached(repo, cache)
    private val drepo = DepartamentoRepository()
    private val dcache = DepartamentoCache()
    private val drepository = DepartamentoRepositoryCached(drepo, dcache)
    private val service = EmpleadoService(repository, drepository)

    @BeforeEach
    fun data() = runBlocking {
        repository.findAll().toList().forEach { repository.delete(it.id) }
        drepository.findAll().toList().forEach { drepository.delete(it.id) }
        drepository.save(departamento.fromDTO())
        val x = repository.save(entity.fromDTO())
    }

    @AfterAll
    fun tearDown() = runBlocking {
        val x = repository.findAll().toList().forEach { repository.delete(it.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val result = service.findAllEmpleados()

        Assertions.assertAll(
            { Assertions.assertEquals(1, result.size) },
            { Assertions.assertEquals(entity.email, result[0].email) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        val result = service.findEmpleadoById(entity.id)

        Assertions.assertAll(
            { Assertions.assertEquals(entity.email, result?.email) },
            { Assertions.assertEquals(entity.nombre, result?.nombre) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNF() = runTest {
        val id = UUID.randomUUID()
        val result = assertThrows<EmpleadoExceptionNotFound> { service.findEmpleadoById(id) }

        Assertions.assertAll(
            { assertEquals("Couldn't find empleado with id $id.", result.message) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val result = service.saveEmpleado(entity)

        Assertions.assertAll(
            { Assertions.assertEquals(entity.email, result.email) },
            { Assertions.assertEquals(entity.nombre, result.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val result = service.deleteEmpleado(entity.id)

        Assertions.assertAll(
            { Assertions.assertEquals(entity.email, result?.email) },
            { Assertions.assertEquals(entity.nombre, result?.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNF() = runTest {
        val id = UUID.randomUUID()
        val result = assertThrows<EmpleadoExceptionBadRequest> { service.deleteEmpleado(id) }

        Assertions.assertAll(
            { assertEquals("Unable to delete empleado $id.", result.message) }
        )
    }
}