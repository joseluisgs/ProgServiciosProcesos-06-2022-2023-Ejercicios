package com.example.routing

import com.example.dto.DepartamentoDto
import com.example.models.Departamento
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
import java.util.*

private val json = Json { ignoreUnknownKeys = true }

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DepartamentoRoutingTest {
    private val config = ApplicationConfig("application.conf")
    private val departamento = Departamento(id = UUID.fromString("044e6ec7-aa6c-46bb-9433-8094ef4ae8bc"), nombre = "Departamento", presupuesto = 12000.50f)
    private val dto = DepartamentoDto(nombre = departamento.nombre, presupuesto = departamento.presupuesto)


    @Test
    @Order(1)
    fun testGetAll() = testApplication {
        environment { config }
        val response = client.get("/departamentos")
        assertEquals(HttpStatusCode.OK, response.status)
    }


    @Test
    @Order(2)
    fun testGetById() = testApplication {
        environment { config }
        val result = client.get("/departamentos/${UUID.randomUUID()}")
        assertEquals(HttpStatusCode.NotFound, result.status)
    }


    @Test
    @Order(3)
    fun testPost() = testApplication {
        environment { config }

        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }

        val response = client.post("/departamentos"){
            contentType(ContentType.Application.Json)
            setBody(dto)
        }

        val result = response.bodyAsText()
        val dept = json.decodeFromString<Departamento>(result)
        assertAll(
            {assertEquals(departamento.nombre, dept.nombre)},
            {assertEquals(departamento.presupuesto, dept.presupuesto)}
        )
    }


    @Test
    @Order(4)
    fun testPut() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }

        var response = client.post("/departamentos"){
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        val result = response.bodyAsText()
        val dept = json.decodeFromString<Departamento>(result)


        response = client.put("/departamentos/${dept.id}"){
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        val put = json.decodeFromString<Departamento>(response.bodyAsText())

        assertAll(
            { assertEquals(dept.id, put.id) },
            {assertEquals(dept.nombre, put.nombre)},
            {assertEquals(dept.presupuesto, put.presupuesto)}
        )
    }


    @Test
    @Order(5)
    fun testPutNotFound() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }


        val response = client.put("/departamentos/${UUID.randomUUID()}"){
            contentType(ContentType.Application.Json)
            setBody(dto)
        }

        assertEquals(HttpStatusCode.NotFound, response.status)
    }


    /**
     * Se necesita estar autorizado y autenticado para realizar el delete de departamentos.
     */
    @Test
    @Order(6)
    fun testDelete() = testApplication {
        environment { config }
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        var response = client.post("/departamentos") {
            contentType(ContentType.Application.Json)
            setBody(dto)
        }
        val dept = json.decodeFromString<Departamento>(response.bodyAsText())

        response = client.delete("/departamentos/${dept.id}")

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }


}