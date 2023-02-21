package com.example.repositories.empleado

import com.example.models.Empleado
import org.koin.core.annotation.Single
import java.util.*

@Single
class EmpleadoRepositoryImpl: EmpleadoRepository {
    private val empleados = mutableMapOf<UUID, Empleado>()

    override fun getEmpleadosByIdDepartamento(id: UUID): List<Empleado> {
        var lista = mutableListOf<Empleado>()
        for (empleado in empleados.values){
            if (empleado.idDepartamento == id){
                lista.add(empleado)
            }
        }
        return lista.toList()
    }

    override fun findEmpleadoByEmail(email: String): Empleado? {
        return empleados.values.firstOrNull { it.email == email }
    }

    override fun update(id: UUID, item: Empleado): Empleado {
        val find = empleados[id]
        find?.let {
            item.id = it.id
            empleados[it.id] = item
        }
        return item
    }

    override fun findAll(): List<Empleado> {
        return empleados.values.toList()
    }

    override fun findById(id: UUID): Empleado? {
        return empleados[id]
    }

    override fun save(item: Empleado): Empleado {
        empleados[item.id] = item
        return item
    }

    override fun delete(id: UUID): Boolean {
        val find = empleados[id]
        find?.let {
            empleados.remove(it.id)
            return true
        }
        return false
    }
}