package com.example.repositories.usuario

import com.example.models.Usuario
import org.koin.core.annotation.Single
import java.util.*

@Single
class UsuarioRepositoryImpl : UsuarioRepository {

    private var list = mutableMapOf<UUID, Usuario>()


    override fun findAll(): List<Usuario> {
        return list.values.toList()
    }

    override fun findById(id: UUID): Usuario? {
        return list[id]
    }

    override fun update(id: UUID, item: Usuario): Usuario {
        val usu = list[id]
        usu?.let {
            item.id = it.id
            list[it.id] = item
        }
        return item    }

    override fun save(item: Usuario): Usuario {
        list[item.id] = item
        return item
    }

    override fun delete(id: UUID): Boolean {
        val usu = list[id]
        usu?.let {
            list.remove(it.id)
            return true
        }
        return false
    }
}