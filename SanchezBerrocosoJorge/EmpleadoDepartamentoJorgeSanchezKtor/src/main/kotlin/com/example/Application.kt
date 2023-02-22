package com.example

import com.example.db.getDepartamentos
import com.example.db.getEmpleados
import com.example.db.getUsuarios
import io.ktor.server.application.*
import com.example.plugins.*
import com.example.repositories.departamentos.DepartamentoRepositoryImpl
import com.example.repositories.empleados.EmpleadosRepository
import com.example.repositories.usuarios.UserRepository
import kotlinx.coroutines.launch

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val repository = UserRepository()
    val listaUser= getUsuarios()
    launch {
        listaUser.forEach { usuario -> repository.save(usuario) }
    }

    val repositoryy = EmpleadosRepository()
    val listaEmpleados= getEmpleados()
    launch {
        listaEmpleados.forEach { empleado -> repositoryy.save(empleado) }
    }

    val repositoryyy = DepartamentoRepositoryImpl()
    val list= getDepartamentos()
    launch {
        list.forEach { depart -> repositoryyy.save(depart) }
    }

    configureKoin()
    configureStorage()
    configureSerialization()
    configureSecurity()
    configureRouting()
    configureSwagger()
}
