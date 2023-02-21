package com.ktordam.services.empleados

import com.ktordam.models.Empleado
import kotlinx.coroutines.flow.Flow


interface IEmpleadosService {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: Int): Empleado
    suspend fun save(empleado: Empleado): Empleado
    suspend fun update(id: Int, empleado: Empleado): Empleado
    suspend fun delete(id: Int): Empleado?
}