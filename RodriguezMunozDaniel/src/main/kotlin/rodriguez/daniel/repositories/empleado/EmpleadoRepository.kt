package rodriguez.daniel.repositories.empleado

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import rodriguez.daniel.model.Empleado
import java.util.*

@Single
@Named("EmpleadoRepository")
class EmpleadoRepository: IEmpleadoRepository {
    private val db = mutableMapOf<UUID, Empleado>()

    override suspend fun findAll(): Flow<Empleado> = withContext(Dispatchers.IO) {
        db.values.asFlow()
    }

    override suspend fun findById(id: UUID): Empleado? = withContext(Dispatchers.IO) {
        db[id]
    }

    override suspend fun save(entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        db[entity.id] = entity
        entity
    }

    override suspend fun delete(id: UUID): Empleado? = withContext(Dispatchers.IO) {
        db.remove(id)
    }
}