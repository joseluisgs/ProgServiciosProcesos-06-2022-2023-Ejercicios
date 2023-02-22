package com.example.repositories.empleados

import com.example.models.Empleado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import java.util.*

@Single
class EmpleadosRepository : IEmpleadoRepository {

    val lista = mutableListOf<Empleado>()

    override suspend fun findAll(): Flow<Empleado> {
        return lista.asFlow()
    }

    override suspend fun findById(id: UUID): Empleado? {
        val item = lista.find { it.id == id }
        return item
    }

    override suspend fun delete(item: Empleado): Empleado? = withContext(Dispatchers.IO) {
        lista.remove(item)
        return@withContext item
    }

    override suspend fun save(item: Empleado): Empleado = withContext(Dispatchers.IO) {
        lista.add(item)
        return@withContext item
    }

    override suspend fun update(item: Empleado): Empleado = withContext(Dispatchers.IO) {
        val it = lista.find { it.id == item.id }
        lista.remove(it)
        lista.add( item)
        return@withContext item
    }
}