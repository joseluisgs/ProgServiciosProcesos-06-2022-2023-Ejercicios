package com.ktordam.db

import com.ktordam.models.Departamento
import com.ktordam.models.Empleado
import com.ktordam.models.Usuario
import java.util.*

/**
 * Función que se encarga de crear Departamentos ficticios.
 */
fun getDepartamentosInit() = listOf(
    Departamento(
        0,
        "Ventas",
        9409f
    ),
    Departamento(
        1,
        "Cargamentos",
        892f
    ),
    Departamento(
        2,
        "Atención al cliente",
        9041f
    )
)

/**
 * Función que se encarga de crear los datos ficticios de Empleados.
 */
fun getEmpleadosInit() = listOf(
    Empleado(
        0,
        "Alejandro Sánchez",
        "alejandrosm@correo.com",
        "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
        getDepartamentosInit()[0]
    ),
    Empleado(
        1,
        "Mireya Sánchez",
        "mireya@correo.com",
        "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
        getDepartamentosInit()[1]
    ),
    Empleado(
        2,
        "Rubén García-Marín",
        "ruben@correo.com",
        "https://media.istockphoto.com/id/1300845620/vector/user-icon-flat-isolated-on-white-background-user-symbol-vector-illustration.jpg?s=612x612&w=0&k=20&c=yBeyba0hUkh14_jgv1OKqIH0CCSWU_4ckRkAoy2p73o=",
        getDepartamentosInit()[2]
    )
)

/**
 * Función que se encrag de crear usaurios ficticios.
 */
fun getUsuariosInit() = listOf(
    Usuario(
        UUID.randomUUID(),
        "jorge",
        "jorge1234",
        Usuario.Role.USER.name
    ),
    Usuario(
        UUID.randomUUID(),
        "alvaro",
        "alvaro1234",
        Usuario.Role.ADMIN.name
    )
)