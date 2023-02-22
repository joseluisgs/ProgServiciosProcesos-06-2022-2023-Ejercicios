package sanchez.mireya.routes

import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import sanchez.mireya.models.Departamento
import java.util.*
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class DepartamentoRoutesKtTest {

    private val departamento = Departamento(
        id = 10,
        budget = 0.0f,
        name = "Test"
    )

    @Test
    @Order(3)
    fun getAll() = testApplication {
        environment { config }
        val response = client.get("/api/departamentos")
        assertEquals(HttpStatusCode.OK, response.status)

    }

    @Test
    @Order(2)
    fun getById() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/api/departamentos") {
            contentType(ContentType.Application.Json)
            setBody(departamento)
        }

        val response = client.get("/api/departamentos/${departamento.id}")
        assertEquals(HttpStatusCode.OK, response.status)

    }

    @Test
    @Order(1)
    fun post() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/api/departamentos") {
            contentType(ContentType.Application.Json)
            setBody(departamento)
        }

        assertEquals(HttpStatusCode.Created, response.status)
    }

    @Test
    @Order(4)
    fun put() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        client.post("/api/departamentos") {
            contentType(ContentType.Application.Json)
            setBody(departamento)
        }

        val response = client.put("/api/departamentos/${departamento.id}") {
            contentType(ContentType.Application.Json)
            setBody(departamento)
        }

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(5)
    fun delete() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        
        client.post("/api/departamentos") {
            contentType(ContentType.Application.Json)
            setBody(departamento)
        }

        val response = client.delete("/api/departamentos/${departamento.id}") {
            contentType(ContentType.Application.Json)
            setBody(departamento)
        }

        assertEquals(HttpStatusCode.NoContent, response.status)
    }
}