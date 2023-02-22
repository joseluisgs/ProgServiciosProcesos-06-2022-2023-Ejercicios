package com.example.db

import com.example.dto.UserRegisterDTO
import com.example.models.Departamento
import com.example.models.Empleado
import com.example.models.User
import java.util.*

fun getDepartamentos() = listOf(
    Departamento(
        id = UUID.randomUUID(),
        name = "Informatica",
        presupuesto = 60.0
    ),
    Departamento(
        id =  UUID.randomUUID(),
        name = "Informatica",
        presupuesto = 90.0
    ),
    Departamento(
        id =  UUID.randomUUID(),
        name = "Informatica",
        presupuesto = 40.0
    )
)

fun getEmpleados() = listOf(
    Empleado(
        id =  UUID.randomUUID(),
        name = "Laura",
        email = "laura@gmail.com",
        departamentoId = getDepartamentos()[0].id,
        avatar = "https://cdn-icons-png.flaticon.com/512/2550/2550260.png"
    ),
    Empleado(
        id = UUID.randomUUID(),
        name = "Pepe",
        email = "pepe@gmail.com",
        departamentoId = getDepartamentos()[1].id,
        avatar = "https://cdn-icons-png.flaticon.com/512/2550/2550260.png"
    )
)

fun getUsuarios() = listOf(
    User(UUID.randomUUID(),"Pepe111", "12345", User.Type.ADMIN.name),
    User(UUID.randomUUID(),"Juan222", "12345", User.Type.USER.name)
)