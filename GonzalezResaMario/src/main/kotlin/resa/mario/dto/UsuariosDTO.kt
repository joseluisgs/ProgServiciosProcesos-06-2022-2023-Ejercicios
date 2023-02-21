package resa.mario.dto

import kotlinx.serialization.Serializable

/**
 * DTO de usuario; usada para el inicio de sesion de un usuario
 *
 * @property username
 * @property password
 */
@Serializable
data class UsuarioDTOLogin(
    val username: String,
    val password: String
)

/**
 * DTO de usuario; usada para el registro de usuarios
 *
 * @property username
 * @property password
 * @property role
 */
@Serializable
data class UsuarioDTORegister(
    val username: String,
    val password: String,
    val role: String,
)

/**
 * DTO de usuario; usada para mostrar al usuario al cliente
 *
 * @property username
 * @property role
 */
@Serializable
data class UsuarioDTOResponse(
    val username: String,
    val role: String,
)