package mendoza.js.service.empleado

import kotlinx.coroutines.flow.Flow
import mendoza.js.models.Empleado
import java.util.*

interface EmpleadoService {
    suspend fun findAll(): Flow<Empleado>
    suspend fun findById(id: UUID): Empleado?
    suspend fun findByNombre(nombre: String): Flow<Empleado>
    suspend fun save(entity: Empleado): Empleado
    suspend fun update(id: UUID, entity: Empleado): Empleado?
    suspend fun delete(id: UUID): Empleado?
}