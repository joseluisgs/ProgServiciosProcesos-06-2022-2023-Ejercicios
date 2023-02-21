package com.example.repositories.usuario

import com.example.models.Usuario
import org.koin.core.annotation.Single
import java.util.*

@Single
class UsuarioRepositoryImpl: UsuarioRepository {
    private var usuarios = mutableMapOf<UUID, Usuario>()
    override fun findByEmail(email: String): Usuario? {
        return usuarios.values.firstOrNull { it.email == email }
    }

    override fun update(id: UUID, item: Usuario): Usuario {
        val find = usuarios[id]
        find?.let {
            item.id = it.id
            usuarios[it.id] = item
        }
        return item
    }

    override fun findAll(): List<Usuario> {
        return usuarios.values.toList()
    }

    override fun findById(id: UUID): Usuario? {
        return usuarios[id]
    }

    override fun save(item: Usuario): Usuario {
        usuarios[item.id] = item
        return item
    }

    override fun delete(id: UUID): Boolean {
        val find = usuarios[id]
        find?.let {
            usuarios.remove(it.id)
            return true
        }
        return false
    }
}