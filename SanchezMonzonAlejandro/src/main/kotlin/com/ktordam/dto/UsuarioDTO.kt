package com.ktordam.dto

import kotlinx.serialization.Serializable

/**
 * Data class que sirve como DTO a la hora de hacer un Login de Usuario.
 */
@Serializable
data class UsuarioLoginDTO(
    val username: String,
    val password: String
)

/**
 * Data class que sirve como DTO a la hora de hacer un register de un nuevo usuario.
 */
@Serializable
data class UsuarioRegisterDTO(
    val username: String,
    val password: String,
    val role: String,
)

/**
 * Data class que sirve para almacenar los campos base de usuario.
 */
@Serializable
data class UsuarioDTO(
    val username: String,
    val role: String,
)

/**
 * Data class para almacenar el usuario con el token.
 */
@Serializable
data class UsuarioTokenDTO(
    val usuario: UsuarioDTO,
    val token: String
)