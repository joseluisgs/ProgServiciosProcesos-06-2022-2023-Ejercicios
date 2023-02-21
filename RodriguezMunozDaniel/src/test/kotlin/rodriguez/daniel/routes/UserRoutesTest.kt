package rodriguez.daniel.routes

import io.ktor.client.call.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import rodriguez.daniel.dto.UserDTO
import rodriguez.daniel.dto.UserDTOandToken
import rodriguez.daniel.dto.UserDTOcreacion
import rodriguez.daniel.dto.UserDTOlogin
import rodriguez.daniel.model.Role
import java.util.*

private val json = Json { ignoreUnknownKeys = true }

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class UserRoutesTest {
    private val config = ApplicationConfig("application.conf")

    private val entity = UserDTOcreacion(
        UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea1707"),
        "test@gmail.com", "test1707", Role.ADMIN
    )
    private val login = UserDTOlogin(entity.email, entity.password)

    @Test
    @Order(1)
    fun registerUserTest() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("ejercicioKtor/users/register") {
            contentType(ContentType.Application.Json)
            setBody(entity)
        }

        assertEquals(response.status, HttpStatusCode.Created)
        val res = json.decodeFromString<UserDTOandToken>(response.bodyAsText())
        assertAll(
            { assertEquals(res.user.role, entity.role) },
            { assertEquals(res.user.email, entity.email) },
            { assertNotNull(res.token) }
        )
    }

    @Test
    @Order(2)
    fun login() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/ejercicioKtor/users/register") {
            contentType(ContentType.Application.Json)
            setBody(entity)
        }

        val responseLogin = client.get("/ejercicioKtor/users/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }

        assertEquals(responseLogin.status, HttpStatusCode.OK)
        val res = json.decodeFromString<UserDTOandToken>(responseLogin.bodyAsText())
        assertAll(
            { assertEquals(res.user.role, entity.role) },
            { assertEquals(res.user.email, entity.email) },
            { assertNotNull(res.token) }
        )
    }

    @Test
    @Order(3)
    fun me() = testApplication {
        environment { config }
        var client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        var response = client.post("/ejercicioKtor/users/register") {
            contentType(ContentType.Application.Json)
            setBody(entity)
        }

        response = client.get("/ejercicioKtor/users/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }
        assertEquals(response.status, HttpStatusCode.OK)
        val res = json.decodeFromString<UserDTOandToken>(response.bodyAsText())

        client = createClient {
            install(ContentNegotiation) {
                json()
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(res.token, res.token)
                    }
                }
            }
        }

        response = client.get("/ejercicioKtor/users/me") {
            contentType(ContentType.Application.Json)
        }
        assertEquals(response.status, HttpStatusCode.OK)
        val resUser = json.decodeFromString<UserDTO>(response.bodyAsText())
        assertAll(
            { assertEquals(resUser.email, entity.email) },
            { assertEquals(resUser.role, entity.role) }
        )
    }

    @Test
    @Order(4)
    fun findAll() = testApplication {
        environment { config }
        var client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        var response = client.post("/ejercicioKtor/users/register") {
            contentType(ContentType.Application.Json)
            setBody(entity)
        }

        response = client.get("/ejercicioKtor/users/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }
        assertEquals(response.status, HttpStatusCode.OK)
        val res = json.decodeFromString<UserDTOandToken>(response.bodyAsText())

        client = createClient {
            install(ContentNegotiation) {
                json()
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(res.token, res.token)
                    }
                }
            }
        }

        response = client.get("/ejercicioKtor/users/list") {
            contentType(ContentType.Application.Json)
        }
        assertEquals(response.status, HttpStatusCode.OK)
    }
}