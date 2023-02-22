package com.example.mappers

import com.example.dto.UsuarioDto
import com.example.models.Rol
import com.example.models.Usuario

fun UsuarioDto.toModel(): Usuario {
    return Usuario(
        nombre=nombre,
        email = email,
        password = password,
        rol= Rol.valueOf(rol),
        avatar = avatar

    )
}