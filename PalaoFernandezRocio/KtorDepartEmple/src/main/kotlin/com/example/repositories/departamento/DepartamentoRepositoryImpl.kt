package com.example.repositories.departamento

import com.example.models.Departamento
import org.koin.core.annotation.Single
import java.util.*

@Single
class DepartamentoRepositoryImpl: DepartamentoRepository {
    private var lista = mutableMapOf<UUID, Departamento>()

    override fun update(id: UUID, item: Departamento): Departamento {
        val find = lista[id]
        find?.let {
            item.id = it.id
            lista[it.id] = item
        }
        return item
    }

    override fun findAll(): List<Departamento> {
        return lista.values.toList()
    }

    override fun findById(id: UUID): Departamento? {
        return lista[id]
    }

    override fun save(item: Departamento): Departamento {
        lista[item.id] = item
        return item
    }

    override fun delete(id: UUID): Boolean {
        val find = lista[id]
        find.let {
            lista.remove(it?.id)
            return true
        }
        return false
    }
}