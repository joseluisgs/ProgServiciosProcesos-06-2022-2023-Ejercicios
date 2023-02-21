package com.ktordam.mappers

import com.ktordam.dto.UsuarioDTO
import com.ktordam.dto.UsuarioRegisterDTO
import com.ktordam.models.Usuario
import com.toxicbakery.bcrypt.Bcrypt
import io.ktor.http.*

/**
 * Función que sirve para hacer un mapeo de Usuario a su respectivo DTO.
 */
fun Usuario.toDTO(): UsuarioDTO {
    return UsuarioDTO(
        username = username,
        role = role
    )
}

/**
 * Función que sirve para hacer un mapeo de UsuarioDTO al modelo de Usuario.
 */
fun UsuarioRegisterDTO.toModel(): Usuario {
    return Usuario(
        username = username,
        password = Bcrypt.hash(password, 12).decodeToString(),
        role = role
    )
}