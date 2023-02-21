package resa.mario.routes

import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import resa.mario.dto.UsuarioDTOLogin
import resa.mario.dto.UsuarioDTORegister
import resa.mario.models.Usuario

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class UsuariosRoutesKtTest {

    private val usuarioRegister = UsuarioDTORegister("MarioTEST", "1234", Usuario.Role.ADMIN.name)

    private val loginDto = UsuarioDTOLogin(
        username = "MarioTEST",
        password = "1234",
    )

    @Test
    @Order(1)
    fun register() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/usuarios/register") {
            contentType(ContentType.Application.Json)
            setBody(usuarioRegister)
        }

        assertEquals(HttpStatusCode.Created, response.status)
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

        client.post("/api/usuarios/register") {
            contentType(ContentType.Application.Json)
            setBody(usuarioRegister)
        }

        val responseLogin = client.post("/api/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDto)
        }

        assertEquals(HttpStatusCode.OK, responseLogin.status)
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

        client.post("/api/usuarios/register") {
            contentType(ContentType.Application.Json)
            setBody(usuarioRegister)
        }

        var response = client.post("/api/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDto)
        }

        // Token
        val res = response.bodyAsText()

        client = createClient {
            install(Auth) {
                bearer {
                    loadTokens {
                        // Load tokens from a local storage and return them as the 'BearerTokens' instance
                        BearerTokens(res, res)
                    }
                }
            }
        }

        response = client.get("/api/usuarios/me")

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(4)
    fun list() = testApplication {
        environment { config }

        var client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        client.post("/api/usuarios/register") {
            contentType(ContentType.Application.Json)
            setBody(usuarioRegister)
        }

        var response = client.post("/api/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDto)
        }

        // Token
        val res = response.bodyAsText()

        client = createClient {
            install(Auth) {
                bearer {
                    loadTokens {
                        // Load tokens from a local storage and return them as the 'BearerTokens' instance
                        BearerTokens(res, res)
                    }
                }
            }
        }

        response = client.get("/api/usuarios/list")

        assertEquals(HttpStatusCode.OK, response.status)
    }
}