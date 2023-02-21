package es.ruymi.services.empleados

import es.ruymi.models.Departamento
import es.ruymi.models.Empleado
import kotlinx.coroutines.flow.Flow
import java.util.*

interface EmpleadoService {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: UUID): Empleado?
    suspend fun save(entity: Empleado): Empleado
    suspend fun update(entity: Empleado): Empleado?
    suspend fun delete(id: UUID): Empleado?
}