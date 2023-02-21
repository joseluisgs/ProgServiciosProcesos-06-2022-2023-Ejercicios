package sanchez.mireya.db

import sanchez.mireya.models.Departamento
import sanchez.mireya.models.Empleado
import sanchez.mireya.models.Role
import sanchez.mireya.models.Usuario
import java.util.*

fun getDepartamentos() = listOf(
    Departamento(1, 12000.5F,"Marketing"),
    Departamento(2, 15000.5F,"Finanzas"),
    Departamento(3, 30000.5F,"Desarrollo")
)

fun getEmpleados() = listOf(
    Empleado(1, "Mireya","mireya@gmail.com","", getDepartamentos()[0]),
    Empleado(2, "Pepe","pepe@gmail.com","", getDepartamentos()[1]),
    Empleado(3, "Ana", "ana@gmail.com","", getDepartamentos()[1]),
    Empleado(4, "Manolo", "manolo@gmail.com",""),
)

fun getUsuarios() = listOf(
    Usuario( UUID.randomUUID(), "mireya", "mireya1234", Role.ADMIN.name),
    Usuario( UUID.randomUUID(), "prueba", "pruebita", Role.USER.name)
)