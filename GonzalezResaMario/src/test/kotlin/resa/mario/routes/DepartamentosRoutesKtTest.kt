package resa.mario.routes

import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import resa.mario.dto.UsuarioDTOLogin
import resa.mario.mappers.toDTO
import resa.mario.models.Departamento
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class DepartamentosRoutesKtTest {

    private val departamento = Departamento(
        nombre = "TEST",
        presupuesto = 100.0
    )

    private val loginDto = UsuarioDTOLogin(
        username = "Mario111",
        password = "1234",
    )

    private val create = departamento.toDTO()

    @Test
    @Order(1)
    fun getAll() = testApplication {
        // Cargamos el entorno
        environment { config }

        // Realizamos la consulta
        val response = client.get("/api/departamentos")

        // Verificamos la respuesta
        assertEquals(HttpStatusCode.OK, response.status)

    }

    @Test
    @Order(2)
    fun post() = testApplication {
        environment { config }

        // Generamos un cliente no generico
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/departamentos") {
            contentType(ContentType.Application.Json)
            setBody(create)
        }

        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    @Order(3)
    fun putNotFound() = testApplication {
        environment { config }

        // Generamos un cliente no generico
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.put("/api/departamentos/${UUID.randomUUID()}") {
            contentType(ContentType.Application.Json)
            setBody(create)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    @Order(4)
    fun deleteNotFound() = testApplication {
        environment { config }

        // Ruta protegida, debemos obtener el token de un ADMIN
        var client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        // Iniciamos sesion
        var response = client.post("api/usuarios/login") {
            contentType(ContentType.Application.Json)
            setBody(loginDto)
        }

        // Token
        val res = response.bodyAsText()

        // Hacemos la operacion con token configurando al cliente de nuevo
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

        response = client.delete("api/departamentos/${UUID.randomUUID()}") {}

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }
}