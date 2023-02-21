package resa.mario.services.empleado

import kotlinx.coroutines.flow.Flow
import resa.mario.models.Empleado
import java.util.UUID

interface EmpleadoService {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: UUID): Empleado
    suspend fun save(entity: Empleado): Empleado
    suspend fun update(id: UUID, entity: Empleado): Empleado
    suspend fun delete(entity: Empleado): Empleado
}