package com.ktordam.routes

import com.ktordam.dto.UsuarioLoginDTO
import com.ktordam.dto.UsuarioRegisterDTO
import com.ktordam.models.Usuario
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class UsuariosRoutesTest {
    private val register = UsuarioRegisterDTO(
        "alejandro",
        "alejandro1234",
        Usuario.Role.ADMIN.name
    )

    private val login = UsuarioLoginDTO(
        "alejandro",
        "alejandro1234"
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

        val registered = client.post("/api/usuarios/register") {
            contentType(ContentType.Application.Json)
            setBody(register)
        }

        assertEquals(HttpStatusCode.Created, registered.status)
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
            setBody(register)
        }

        val loged = client.post("/api/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }

        assertEquals(HttpStatusCode.OK, loged.status)
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
            setBody(register)
        }

        var loged = client.post("/api/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }

        val result = loged.bodyAsText()

        client = createClient {
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(result, result)
                    }
                }
            }
        }

        loged = client.get("/api/usuarios/me")

        assertEquals(HttpStatusCode.OK, loged.status)
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
            setBody(register)
        }

        var loged = client.post("/api/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody(login)
        }

        val result = loged.bodyAsText()

        client = createClient {
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(result, result)
                    }
                }
            }
        }

        loged = client.get("/api/usuarios/list")

        assertEquals(HttpStatusCode.OK, loged.status)
    }
}