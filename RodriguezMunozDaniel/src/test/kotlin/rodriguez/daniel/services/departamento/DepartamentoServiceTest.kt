package rodriguez.daniel.services.departamento

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import rodriguez.daniel.dto.DepartamentoDTOcreacion
import rodriguez.daniel.exception.DepartamentoExceptionNotFound
import rodriguez.daniel.mappers.fromDTO
import rodriguez.daniel.repositories.departamento.DepartamentoRepository
import rodriguez.daniel.repositories.departamento.DepartamentoRepositoryCached
import rodriguez.daniel.repositories.empleado.EmpleadoRepository
import rodriguez.daniel.repositories.empleado.EmpleadoRepositoryCached
import rodriguez.daniel.services.cache.departamento.DepartamentoCache
import rodriguez.daniel.services.cache.empleado.EmpleadoCache
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DepartamentoServiceTest {
    private val entity = DepartamentoDTOcreacion(
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0003"),
        "Departamento test", 69_420.69
    )

    private val erepo = EmpleadoRepository()
    private val ecache = EmpleadoCache()
    private val erepository = EmpleadoRepositoryCached(erepo, ecache)
    private val repo = DepartamentoRepository()
    private val cache = DepartamentoCache()
    private val repository = DepartamentoRepositoryCached(repo, cache)
    private val service = DepartamentoService(erepository, repository)

    @BeforeEach
    fun data() = runBlocking {
        erepository.findAll().toList().forEach { erepository.delete(it.id) }
        repository.findAll().toList().forEach { repository.delete(it.id) }
        val x = repository.save(entity.fromDTO())
    }

    @AfterAll
    fun tearDown() = runBlocking {
        val x = repository.findAll().toList().forEach { repository.delete(it.id) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findAll() = runTest {
        val result = service.findAllDepartamentos()

        Assertions.assertAll(
            { Assertions.assertEquals(1, result.size) },
            { Assertions.assertEquals(entity.presupuesto, result[0].presupuesto) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        val result = service.findDepartamentoById(entity.id)

        Assertions.assertAll(
            { Assertions.assertEquals(entity.presupuesto, result.presupuesto) },
            { Assertions.assertEquals(entity.nombre, result.nombre) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNF() = runTest {
        val id = UUID.randomUUID()
        val result = assertThrows<DepartamentoExceptionNotFound> { service.findDepartamentoById(id) }

        Assertions.assertAll(
            { Assertions.assertEquals("Couldn't find departamento with id $id.", result.message) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val result = service.saveDepartamento(entity)

        Assertions.assertAll(
            { Assertions.assertEquals(entity.presupuesto, result.presupuesto) },
            { Assertions.assertEquals(entity.nombre, result.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val result = service.deleteDepartamento(entity.id)

        Assertions.assertAll(
            { Assertions.assertEquals(entity.presupuesto, result.presupuesto) },
            { Assertions.assertEquals(entity.nombre, result.nombre) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNF() = runTest {
        val id = UUID.randomUUID()
        val result = assertThrows<DepartamentoExceptionNotFound> { service.deleteDepartamento(id) }

        Assertions.assertAll(
            { Assertions.assertEquals("Couldn't find departamento with id $id.", result.message) }
        )
    }
}