package resa.mario.dto

import kotlinx.serialization.Serializable

/**
 * DTO de departamento; usada para mostrar cierta informacion al cliente, y para el registro de nuevos departamentos
 *
 * @property nombre
 * @property presupuesto
 */
@Serializable
data class DepartamentoDTO(
    val nombre: String,
    val presupuesto: Double
)