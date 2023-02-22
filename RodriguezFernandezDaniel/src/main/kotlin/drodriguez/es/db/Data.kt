package drodriguez.es.db

import drodriguez.es.dto.UserCreateDto
import drodriguez.es.models.Departamento
import drodriguez.es.models.Empleado
import drodriguez.es.models.User
import java.util.*

fun getDepartamentos() = listOf(
    Departamento(
        id = UUID.fromString("e85fd3ed-175e-45f0-a765-7af1c52e26cc"),
        nombreDepartamento = "Recursos Humanos",
    ),
    Departamento(
        id = UUID.fromString("f92866ab-f286-43aa-867f-6555d1019610"),
        nombreDepartamento = "Informatica",
    )
)

fun getEmpleados() = listOf(
    Empleado(
        id = UUID.fromString("194f10f3-826e-4d37-9e41-552566f60513"),
        nombre = "Dani",
        email = "daniiiiieee@gmail.com",
        avatar = "https://cdn-icons-png.flaticon.com/512/5087/5087579.png",
        departamentoId = getDepartamentos()[0].id,
        ),
    Empleado(
        id = UUID.fromString("54d75e62-d1ce-46a8-9b10-4879c95c2de5"),
        nombre = "Jorge",
        email = "jorgee.jorgeee@gmail.com",
        avatar = "https://cdn-icons-png.flaticon.com/512/5087/5087579.png",
        departamentoId = getDepartamentos()[1].id,
        )
)

fun getUsuarios() = listOf(
    UserCreateDto("idanirf", "654321", User.Rol.JEFE.name),
    UserCreateDto("jorch", "654321", User.Rol.USER.name),
)

