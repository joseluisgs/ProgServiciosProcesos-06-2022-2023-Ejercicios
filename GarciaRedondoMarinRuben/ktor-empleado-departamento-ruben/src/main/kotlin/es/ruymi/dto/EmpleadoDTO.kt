package es.ruymi.dto

import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class EmpleadoDTO(
    val id: String? = UUID.randomUUID().toString(),
    val nombre: String,
    val email: String,
    val avatar: String,
    val departamento: DepartamentoDTO
)
