package resa.mario.models

import java.util.UUID

/**
 * Modelo para departamento
 *
 * @property id
 * @property nombre
 * @property presupuesto
 */
data class Departamento(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val presupuesto: Double
) {
    override fun toString(): String {
        return "Departamentos(id=$id, nombre='$nombre', presupuesto=$presupuesto)"
    }
}
