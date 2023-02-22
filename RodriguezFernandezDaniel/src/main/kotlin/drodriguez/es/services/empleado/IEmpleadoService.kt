package drodriguez.es.services.empleado

import drodriguez.es.models.Empleado
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IEmpleadoService {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: UUID): Empleado
    suspend fun save(entity: Empleado): Empleado
    suspend fun update(id: UUID, entity: Empleado): Empleado
    suspend fun delete(entity: Empleado): Empleado
}