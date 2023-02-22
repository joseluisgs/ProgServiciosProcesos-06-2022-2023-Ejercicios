package es.ruymi.dto

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DepartamentoDTO(
    val id: String? = UUID.randomUUID().toString(),
    val nombre: String,
    val presupuesto: Int
)
