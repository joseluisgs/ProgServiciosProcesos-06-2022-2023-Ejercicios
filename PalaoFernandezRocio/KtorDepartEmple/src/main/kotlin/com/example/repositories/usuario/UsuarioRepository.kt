package com.example.repositories.usuario

import com.example.models.Usuario
import com.example.repositories.ICRUD
import java.util.*

interface UsuarioRepository: ICRUD<Usuario, UUID> {
    fun findByEmail(email: String): Usuario?
}