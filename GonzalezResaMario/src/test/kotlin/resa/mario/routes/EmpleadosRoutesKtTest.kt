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
import resa.mario.dto.EmpleadoDTO
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class EmpleadosRoutesKtTest {

    private val create = EmpleadoDTO(
        nombre = "Mario_TEST",
        email = "mario_test@gmail.com",
        departamentoId = null,
        avatar = ""
    )

    @Test
    @Order(1)
    fun getAll() = testApplication {
        // Cargamos el entorno
        environment { config }

        // Realizamos la consulta
        val response = client.get("/api/empleados")

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

        val response = client.post("/api/empleados") {
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

        val response = client.put("/api/empleados/${UUID.randomUUID()}") {
            contentType(ContentType.Application.Json)
            setBody(create)
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    @Order(4)
    fun deleteNotFound() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.delete("api/empleados/${UUID.randomUUID()}") {}

        assertEquals(HttpStatusCode.NotFound, response.status)
    }

}