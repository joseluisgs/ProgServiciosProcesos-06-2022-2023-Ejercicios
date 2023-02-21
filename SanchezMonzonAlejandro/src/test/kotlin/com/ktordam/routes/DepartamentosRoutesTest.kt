package com.ktordam.routes

import com.ktordam.models.Departamento
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.*
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class DepartamentosRoutesTest {
    private val departamento = Departamento(
        0,
        "Prueba",
        0f
    )

    @Test
    @Order(1)
    fun findAll() = testApplication {
        environment { config }

        val response = client.get("/api/departamentos")

        Assertions.assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(2)
    fun findById() = testApplication {
        environment { config }

        val result = client.get("api/departamentos/${departamento.id}")

        Assertions.assertEquals(HttpStatusCode.OK, result.status)
    }

    @Test
    @Order(3)
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

        Assertions.assertEquals(HttpStatusCode.Created, response.status)
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

        val response = client.put("/api/departamentos/${departamento.id}") {
            contentType(ContentType.Application.Json)
            setBody(departamento)
        }

        Assertions.assertEquals(HttpStatusCode.OK, response.status)
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

        val response = client.delete("api/departamentos/${departamento.id}")

        Assertions.assertEquals(HttpStatusCode.NoContent, response.status)
    }
}