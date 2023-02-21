package resa.mario.dto

import kotlinx.serialization.Serializable

/**
 * DTO de empleado; usada para mostrar cierta informacion al cliente, y para el registro de nuevos empleados
 *
 * @property nombre
 * @property email
 * @property departamentoId
 * @property avatar
 */
@Serializable
data class EmpleadoDTO(
    val nombre: String,
    val email: String,
    val departamentoId: String? = null,
    val avatar: String?
)