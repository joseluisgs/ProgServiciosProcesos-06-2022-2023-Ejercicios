package sanchez.mireya.mappers

import sanchez.mireya.dto.UsuarioRegisterDTO
import sanchez.mireya.dto.UsuarioDTO
import sanchez.mireya.models.Usuario
import sanchez.mireya.services.Cifrador
import java.util.*

fun Usuario.toDTO(): UsuarioDTO {
    return UsuarioDTO(
        username = username,
        role = role,
        createdAt = createdAt.toString()
    )
}

fun UsuarioRegisterDTO.toUsuario(): Usuario {
    return Usuario(
        id = UUID.randomUUID(),
        username = username,
        password = Cifrador.cipher(password),
        role = role
    )
}