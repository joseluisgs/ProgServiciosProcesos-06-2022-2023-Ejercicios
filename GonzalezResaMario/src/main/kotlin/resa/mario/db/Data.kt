package resa.mario.db

import resa.mario.dto.UsuarioDTORegister
import resa.mario.models.Departamento
import resa.mario.models.Empleado
import resa.mario.models.Usuario
import java.util.*

fun getDepartamentos() = listOf(
    Departamento(
        id = UUID.fromString("1d89e51a-af79-11ed-afa1-0242ac120002"),
        nombre = "Interfaces",
        presupuesto = 500.0
    ),
    Departamento(
        id = UUID.fromString("2fa8d67a-af79-11ed-afa1-0242ac120002"),
        nombre = "Administracion",
        presupuesto = 700.0
    ),
    Departamento(
        id = UUID.fromString("36a508e0-af79-11ed-afa1-0242ac120002"),
        nombre = "PSP",
        presupuesto = 400.0
    )
)

fun getEmpleados() = listOf(
    Empleado(
        id = UUID.fromString("db7fbce4-b095-11ed-afa1-0242ac120002"),
        nombre = "Mario",
        email = "mario@gmail.com",
        departamentoId = getDepartamentos()[0].id,
        avatar = "https://cdn-icons-png.flaticon.com/512/2550/2550260.png"
    ),
    Empleado(
        id = UUID.fromString("8f26fa30-b09d-11ed-afa1-0242ac120002"),
        nombre = "Alysys",
        email = "alysys@gmail.com",
        departamentoId = getDepartamentos()[1].id,
        avatar = "https://cdn-icons-png.flaticon.com/512/2550/2550260.png"
    ),
    Empleado(
        nombre = "Vincent",
        email = "vincent@gmail.com",
        departamentoId = getDepartamentos()[1].id,
        avatar = "https://cdn-icons-png.flaticon.com/512/2550/2550260.png"
    ),
    Empleado(
        nombre = "Kratos",
        email = "kratos@gmail.com",
        avatar = "https://cdn-icons-png.flaticon.com/512/2550/2550260.png"
    ),
)

fun getUsuarios() = listOf(
    UsuarioDTORegister("Mario111", "1234", Usuario.Role.ADMIN.name),
    UsuarioDTORegister("Alysys222", "1234", Usuario.Role.USER.name)
)