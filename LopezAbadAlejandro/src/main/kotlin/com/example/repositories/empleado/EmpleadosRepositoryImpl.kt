package com.example.repositories.empleado

import com.example.models.Empleado
import org.koin.core.annotation.Single
import java.util.*
@Single
class EmpleadosRepositoryImpl:EmpleadoRepository {
    private val list = mutableMapOf<UUID, Empleado>()

    override fun findAll(): List<Empleado> {
        return list.values.toList()
    }

    override fun findById(id: UUID): Empleado? {
        return list[id]
    }

    override fun update(id: UUID, item: Empleado): Empleado {
        val empl = list[id]
        empl?.let {
            item.id = it.id
            list[it.id] = item
        }
        return item
    }

    override fun save(item: Empleado): Empleado {
        list[item.id] = item
        return item
    }

    override fun delete(id: UUID): Boolean {
        val empl = list[id]
        empl?.let {
            list.remove(it.id)
            return true
        }
        return false
    }
}