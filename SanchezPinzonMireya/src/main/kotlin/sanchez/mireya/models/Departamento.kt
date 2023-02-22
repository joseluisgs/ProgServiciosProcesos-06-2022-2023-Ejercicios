package sanchez.mireya.models

import kotlinx.serialization.Serializable

@Serializable
data class Departamento(
    val id: Int,
    val budget: Float,
    val name: String,
)