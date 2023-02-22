package com.example.routing

import com.example.models.Departamento
import com.example.repositories.departamento.DepartamentoRepositoryImpl
import com.example.repositories.empleado.EmpleadosRepositoryImpl
import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.departamentoRoutes(){
    val repDepartamento:DepartamentoRepositoryImpl by inject()
    val repEmpleados:EmpleadosRepositoryImpl by inject()

    routing {

            get("/departamentos",{
            description = "Get All de Departamentos"
            response {
                HttpStatusCode.OK to {
                    description = "Listas de departamentos"
                    body<List<Departamento>> { description = "Listado" }
                }
            }
        }){

            call.respond(HttpStatusCode.OK, repDepartamento.findAll())
        }


    }
}