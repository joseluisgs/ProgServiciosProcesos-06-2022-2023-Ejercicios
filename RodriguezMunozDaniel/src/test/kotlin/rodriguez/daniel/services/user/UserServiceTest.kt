package rodriguez.daniel.services.user

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertAll
import rodriguez.daniel.dto.UserDTOcreacion
import rodriguez.daniel.exception.UserUnauthorizedException
import rodriguez.daniel.mappers.fromDTO
import rodriguez.daniel.model.Role
import rodriguez.daniel.repositories.user.UserRepository
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    private val entity = UserDTOcreacion(
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0000"),
        "user@gmail.com", "1234567", Role.EMPLEADO
    )

    private val repository = UserRepository()
    private val service = UserService(repository)

    @BeforeEach
    fun data() = runBlocking {
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
        val result = service.findAllUsers()

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals(entity.email, result[0].email) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findById() = runTest {
        val result = service.findUserById(entity.id)

        assertAll(
            { assertEquals(entity.email, result?.email) },
            { assertEquals(entity.role, result?.role) },
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun findByIdNF() = runTest {
        val result = service.findUserById(UUID.randomUUID())

        assertAll(
            { assertNull(result) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkEmailAndPassword() = runTest {
        val result = service.checkEmailAndPassword(entity.email, entity.password)

        assertAll(
            { assertEquals(entity.email, result.email) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkEmailAndPasswordNF() = runTest {
        val result = assertThrows<UserUnauthorizedException> {
            service.checkEmailAndPassword("uwu", entity.password)
        }

        assertAll(
            { assertEquals("Incorrect email or password.", result.message) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun save() = runTest {
        val result = service.saveUser(entity)

        assertAll(
            { assertEquals(entity.email, result.email) },
            { assertEquals(entity.role, result.role) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun delete() = runTest {
        val result = service.deleteUser(entity.id)

        assertAll(
            { assertEquals(entity.email, result?.email) },
            { assertEquals(entity.role, result?.role) }
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteNF() = runTest {
        val result = service.deleteUser(UUID.randomUUID())

        assertAll(
            { assertNull(result) }
        )
    }
}