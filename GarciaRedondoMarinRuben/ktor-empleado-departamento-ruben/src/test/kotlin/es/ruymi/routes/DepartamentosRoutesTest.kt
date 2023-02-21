package es.ruymi.routes

import es.ruymi.mappers.toDto
import es.ruymi.models.Departamento
import io.ktor.client.plugins.compression.*
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
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class DepartamentosRoutesTest {
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @TestMethodOrder(MethodOrderer.OrderAnnotation::class)
    internal class DepartamentosRoutesKtTest {

        private val departamento = Departamento(
            UUID.fromString("7d737184-c159-49c1-a083-f42fecd12a53"),
            nombre = "TestInformacion",
            presupuesto = 100
        )

        @Test
        @Order(1)
        fun post() = testApplication {
            environment { config }
            val client = createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
            val res = client.post("/api/departamento") {
                contentType(ContentType.Application.Json)
                setBody(departamento.toDto())
            }
            assertEquals(HttpStatusCode.Created, res.status)
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
            val res = client.post("/api/departamento"){
                contentType(ContentType.Application.Json)
                setBody(departamento.toDto())
            }
            val res2 = client.get("/api/departamentos/${departamento.id}")
            assertEquals(HttpStatusCode.Created, res2.status)
        }

        @Test
        @Order(3)
        fun getAll() = testApplication {
            environment { config }
            val res = client.get("/api/departamento")
            assertEquals(HttpStatusCode.OK, res.status)
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
                setBody(departamento.toDto())
            }
            val res = client.put("/api/departamentos/${departamento.id}") {
                contentType(ContentType.Application.Json)
                setBody(departamento.toDto())
            }
            assertEquals(HttpStatusCode.OK, res.status)
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
            client.post("/api/departamento") {
                contentType(ContentType.Application.Json)
                setBody(departamento.toDto())
            }
            val res = client.delete("/api/departamento/${departamento.id}") {
                contentType(ContentType.Application.Json)
                setBody(departamento.toDto())
            }
            assertEquals(HttpStatusCode.NoContent, res.status)
        }

    }
}