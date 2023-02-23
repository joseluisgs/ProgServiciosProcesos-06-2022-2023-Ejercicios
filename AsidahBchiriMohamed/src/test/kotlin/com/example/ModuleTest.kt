package com.example;

import com.example.module

import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class ModuleTest {

    @Test
    fun testGet() = testApplication {
        application {
            module()
        }
        client.get("/").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostApiStorage() = testApplication {
        application {
            module()
        }
        client.post("/api/storage").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetApiStorageFilename() = testApplication {
        application {
            module()
        }
        client.get("/api/storage/{fileName}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetApiStorageCheck() = testApplication {
        application {
            module()
        }
        client.get("/api/storage/check").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetDepartamentos() = testApplication {
        application {
            module()
        }
        client.get("/departamentos").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPatchDepartamentos() = testApplication {
        application {
            module()
        }
        client.patch("/departamentos").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostDepartamentos() = testApplication {
        application {
            module()
        }
        client.post("/departamentos").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetDepartamentosId() = testApplication {
        application {
            module()
        }
        client.get("/departamentos/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetDepartamentosHola() = testApplication {
        application {
            module()
        }
        client.get("/departamentos/hola").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetEmpleados() = testApplication {
        application {
            module()
        }
        client.get("/empleados").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostEmpleados() = testApplication {
        application {
            module()
        }
        client.post("/empleados").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteEmpleadosId() = testApplication {
        application {
            module()
        }
        client.delete("/empleados/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetEmpleadosId() = testApplication {
        application {
            module()
        }
        client.get("/empleados/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetEmpleadosEmail() = testApplication {
        application {
            module()
        }
        client.get("/empleados/{email}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPatchEmpleadosId() = testApplication {
        application {
            module()
        }
        client.patch("/empleados/{id}").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetEmpleadosHola() = testApplication {
        application {
            module()
        }
        client.get("/empleados/hola").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostEmpleadosLogin() = testApplication {
        application {
            module()
        }
        client.post("/empleados/login").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostEmpleadosRegister() = testApplication {
        application {
            module()
        }
        client.post("/empleados/register").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testGetJsonKotlinxserialization() = testApplication {
        application {
            module()
        }
        client.get("/json/kotlinx-serialization").apply {
            TODO("Please write your test here")
        }
    }
}