package resa.mario.models

import java.util.UUID

/**
 * Modelo para empleado
 *
 * @property id
 * @property nombre
 * @property email
 * @property departamentoId
 * @property avatar
 */
data class Empleado(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val email: String,
    var departamentoId: UUID? = null,
    var avatar: String?
) {
    override fun toString(): String {
        return "Empleado(id=$id, nombre='$nombre', email='$email', departamentoId=$departamentoId, avatar='$avatar')"
    }
}
