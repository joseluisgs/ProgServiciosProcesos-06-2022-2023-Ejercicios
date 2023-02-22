package sanchez.mireya.models

import kotlinx.serialization.Serializable

@Serializable
data class Empleado(
    val id: Int,
    val name: String,
    val email: String,
    val avatar: String,
    val departament: Departamento? = null
)
