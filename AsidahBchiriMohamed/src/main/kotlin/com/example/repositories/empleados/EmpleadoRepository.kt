package com.example.repositories.empleados

import com.example.Data
import com.example.models.Empleado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Single
import java.util.*

@Single
class EmpleadoRepository : IEmpleadoRepository {

    val repository = mutableListOf<Empleado>()
    override suspend fun findByEmail(email: String): Empleado? {
        return Data.repository.firstOrNull { it.email == email }
    }

    override suspend fun findAll(): Flow<Empleado> {
        return Data.repository.asFlow()
    }

    override suspend fun findById(id: UUID): Empleado? {
        return Data.repository.firstOrNull { it.uuid == id }
    }

    override suspend fun save(item: Empleado): Empleado? {
        if (!Data.repository.contains(item)) {
            Data.repository.add(item)
            return item
        }
        return null
    }

    override suspend fun update(item: Empleado): Empleado? {
        if (Data.repository.contains(item)) {
            Data.repository.remove(item)
            Data.repository.add(item)
            return item
        }
        return null
    }

    override suspend fun delete(item: Empleado): Boolean {
        return Data.repository.remove(item)
    }

    override suspend fun deleteAll() {
        return Data.repository.clear()
    }

}