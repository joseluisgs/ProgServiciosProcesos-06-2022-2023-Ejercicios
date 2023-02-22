package com.example.repositories.departamentos

import com.example.Data
import com.example.models.Departamento
import com.example.models.Empleado
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Single
import java.util.*

@Single
class DepartamentoRepository : IDepartamentoRepository {
    override suspend fun findAll(): Flow<Departamento> {
        return Data.departamentos.asFlow()
    }

    override suspend fun findById(id: UUID): Departamento? {
        return Data.departamentos.firstOrNull { it.uuid == id }
    }

    override suspend fun save(item: Departamento): Departamento? {
        if (!Data.departamentos.contains(item)) {
            Data.departamentos.add(item)
            return item
        }
        return null
    }

    override suspend fun update(item: Departamento): Departamento? {
        if (Data.departamentos.contains(item)) {
            Data.departamentos.remove(item)
            Data.departamentos.add(item)
            return item
        }
        return null
    }

    override suspend fun delete(item: Departamento): Boolean {
        return Data.departamentos.remove(item)
    }

    override suspend fun deleteAll() {
        return Data.departamentos.clear()
    }
}