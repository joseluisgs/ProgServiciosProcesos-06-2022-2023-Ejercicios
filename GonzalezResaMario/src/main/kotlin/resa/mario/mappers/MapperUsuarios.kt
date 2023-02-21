package resa.mario.mappers

import resa.mario.dto.UsuarioDTORegister
import resa.mario.dto.UsuarioDTOResponse
import resa.mario.models.Usuario
import resa.mario.utils.Cifrador

/**
 * Funcion que permite pasar de modelo a DTO
 *
 * @return [UsuarioDTOResponse]
 */
fun Usuario.toDTO(): UsuarioDTOResponse {
    return UsuarioDTOResponse(
        username = username,
        role = role,
    )
}

/**
 * Funcion que permite pasar de DTO [UsuarioDTORegister] a modelo
 *
 * @return [Usuario]
 */
fun UsuarioDTORegister.toUsuario(): Usuario {
    return Usuario(
        username = username,
        password = Cifrador.cipher(password),
        role = role
    )
}