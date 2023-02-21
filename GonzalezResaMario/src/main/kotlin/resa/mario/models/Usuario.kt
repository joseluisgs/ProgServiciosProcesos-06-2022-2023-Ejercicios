package resa.mario.models

import java.util.UUID

/**
 * Modelo para usuario
 *
 * @property id
 * @property username
 * @property password
 * @property role
 */
data class Usuario(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val password: String,
    val role: String = Role.USER.name
) {
    /**
     * Clase enum para el role del usuario
     *
     */
    enum class Role {
        USER, ADMIN
    }

    override fun toString(): String {
        return "Usuario(id=$id, username='$username', password='$password', role='$role')"
    }

}
