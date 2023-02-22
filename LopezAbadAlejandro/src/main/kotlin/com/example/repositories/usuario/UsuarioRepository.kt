package com.example.repositories.usuario

import com.example.models.Usuario
import com.example.repositories.CrudRepository
import java.util.*

interface UsuarioRepository:CrudRepository<Usuario, UUID> {
}