package com.example.repositories.departamento

import com.example.models.Departamento
import org.koin.core.annotation.Single
import java.util.*

@Single
class DepartamentoRepositoryImpl : DepartamentoRepository {

    private var list = mutableMapOf<UUID, Departamento>()


    override fun findAll(): List<Departamento> {
        return list.values.toList()
    }

    override fun findById(id: UUID): Departamento? {
        return list[id]
    }

    override fun update(id: UUID, item: Departamento): Departamento {
        val dep = list[id]
        dep?.let {
            item.id = it.id
            list[it.id] = item
        }
        return item
    }

    override fun save(item: Departamento): Departamento {
        list[item.id] = item
        return item
    }

    override fun delete(id: UUID): Boolean {
        val dep = list[id]
        dep.let {
            list.remove(it?.id)
            return true
        }
        return false
    }
}