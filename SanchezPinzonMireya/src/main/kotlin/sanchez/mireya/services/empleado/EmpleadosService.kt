package sanchez.mireya.services.empleado

import kotlinx.coroutines.flow.Flow
import sanchez.mireya.models.Empleado

interface EmpleadosService {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: Int): Empleado
    suspend fun save(entity: Empleado): Empleado
    suspend fun update(id: Int, entity: Empleado): Empleado
    suspend fun delete(id: Int): Empleado
}