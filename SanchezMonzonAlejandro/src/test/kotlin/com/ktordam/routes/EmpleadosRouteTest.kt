package com.ktordam.routes

import com.ktordam.models.Departamento
import com.ktordam.models.Empleado
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
import java.util.*
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
internal class EmpleadosRouteTest {
    private val departamento = Departamento(
        0,
        "Prueba",
        0f
    )

    private val empleado = Empleado(
        0,
        "alejandro",
        "alejandro@correo.com",
        "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
        departamento
    )

    @Test
    @Order(1)
    fun findAll() = testApplication {
        environment { config }

        val response = client.get("/api/empleados")

        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    @Order(2)
    fun findById() = testApplication {
        environment { config }

        val result = client.get("api/empleados/${empleado.id}")

        assertEquals(HttpStatusCode.OK, result.status)
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

        val response = client.post("/api/empleados") {
            contentType(ContentType.Application.Json)
            setBody(empleado)
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

        val response = client.put("/api/empleados/${empleado.id}") {
            contentType(ContentType.Application.Json)
            setBody(empleado)
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

        val response = client.delete("api/empleados/${departamento.id}")

        assertEquals(HttpStatusCode.NoContent, response.status)
    }
}