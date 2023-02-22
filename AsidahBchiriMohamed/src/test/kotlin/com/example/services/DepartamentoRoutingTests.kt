package com.example.services

import com.example.dtos.DepartamentoCreateDto
import com.example.models.Departamento
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import org.eclipse.jetty.http.HttpStatus
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DepartamentoRoutingTests {
    val departamento = Departamento(name = "Testing")
    val departamentoCreate = DepartamentoCreateDto(name = "Testing")
    val config = ApplicationConfig("application.conf")

    @Test
    @Order(1)
    fun findAllTest() = testApplication {
        environment { config }
        val response = client.get("/empleados")
        assertEquals(HttpStatusCode.OK, response.status)
    }




}