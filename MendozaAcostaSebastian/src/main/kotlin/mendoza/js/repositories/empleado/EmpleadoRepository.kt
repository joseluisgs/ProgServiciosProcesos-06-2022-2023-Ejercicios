package mendoza.js.repositories.empleado

import kotlinx.coroutines.flow.Flow
import mendoza.js.models.Empleado
import mendoza.js.repositories.CrudRepository
import java.util.UUID

interface EmpleadoRepository : CrudRepository<Empleado, UUID> {
    suspend fun findByNombre(nombre: String): Flow<Empleado>
}