package es.ruymi.models

import java.util.*


data class Empleado(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val email: String,
    val avatar: String,
    val departamento: Departamento
) {
}