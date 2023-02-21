package rodriguez.daniel.db

import rodriguez.daniel.dto.DepartamentoDTOcreacion
import rodriguez.daniel.dto.EmpleadoDTOcreacion
import rodriguez.daniel.dto.UserDTOcreacion
import rodriguez.daniel.model.Role
import java.util.*

val admin = UserDTOcreacion(
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea83aa"),
    "loli@gmail.com", "loli1707", Role.ADMIN
)
val user = UserDTOcreacion(
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0000"),
    "user@gmail.com", "1234567", Role.EMPLEADO
)

val empleado1 = EmpleadoDTOcreacion(
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0001"),
    "Nombre Generico", "empleado1@gmail.com", "",
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0003")
)
val empleado2 = EmpleadoDTOcreacion(
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0002"),
    "Juan Manodeobra", "empleado2@gmail.com", "",
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0003")
)

val departamento1 = DepartamentoDTOcreacion(
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0003"),
    "Departamento falso", 69_420.69
)
val departamento2 = DepartamentoDTOcreacion(
    UUID.fromString("93a98d69-6da6-48a7-b34f-05b596ea0004"),
    "Departamento falso 2", 15.0
)

fun users() = listOf(admin, user)
fun empleados() = listOf(empleado1, empleado2)
fun departamentos() = listOf(departamento1, departamento2)