package com.example.repositories.empleados

import com.example.models.Empleado
import com.example.models.Notification
import kotlinx.coroutines.flow.Flow

interface EmpleadoRepository {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: Long): Empleado?
    suspend fun save(empleado: Empleado): Empleado
    suspend fun delete(empleado: Empleado): Boolean
    suspend fun update(id: Long, empleado: Empleado): Empleado

    fun addSuscriptor(id: Int, suscriptor: suspend (Notification<Empleado>) -> Unit)
    fun removeSuscriptor(id: Int)
}