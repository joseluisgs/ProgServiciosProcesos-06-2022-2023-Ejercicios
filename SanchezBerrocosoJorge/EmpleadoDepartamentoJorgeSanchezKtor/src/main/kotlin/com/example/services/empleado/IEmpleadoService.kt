package com.example.services.empleado

import com.example.models.Empleado
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IEmpleadoService {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: UUID): Empleado
    suspend fun save(empleado: Empleado): Empleado
    suspend fun delete(empleado: Empleado): Empleado
    suspend fun update(empleado: Empleado): Empleado
}