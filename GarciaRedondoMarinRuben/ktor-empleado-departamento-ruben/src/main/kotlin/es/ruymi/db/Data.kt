package es.ruymi.db

import es.ruymi.models.Departamento
import es.ruymi.models.Empleado
import java.util.*


fun getDepartamentos(): List<Departamento> {
    return listOf(
        Departamento(
            UUID.fromString("7d73718d-4569-49c1-a083-f4247f312a53"),
            "Inoformación",
            200
        ),
        Departamento(
            UUID.fromString("7d73718d-4569-49c1-a083-f42fecd12a53"),
            "Moviles",
            150
        ),
        Departamento(
            UUID.fromString("7d737184-c159-49c1-a083-f42fecd12a53"),
            "Reparacion",
            270
        ),
    )
}

fun getEmpleados(): List<Empleado>{
    return listOf(
        Empleado(
            UUID.randomUUID(),
            "Mireya",
            "mireya@gmail.com",
            "avatar1",
            Departamento(
                UUID.fromString("7d737184-c159-49c1-a083-f42fecd12a53"),
                "Reparacion",
                270
            ),
        ),
        Empleado(
            UUID.randomUUID(),
            "Alejandro",
            "alejandro@gmail.com",
            "avatar2",
            Departamento(
                UUID.fromString("7d73718d-4569-49c1-a083-f42fecd12a53"),
                "Moviles",
                150
            ),
        ),
        Empleado(
            UUID.randomUUID(),
            "Ruben",
            "ruben@gmail.com",
            "avatar3",
            Departamento(
                UUID.fromString("4r73718d-4569-49c1-a083-f4247f312a53"),
                "Inoformación",
                200
            ),
        ),
    )
}