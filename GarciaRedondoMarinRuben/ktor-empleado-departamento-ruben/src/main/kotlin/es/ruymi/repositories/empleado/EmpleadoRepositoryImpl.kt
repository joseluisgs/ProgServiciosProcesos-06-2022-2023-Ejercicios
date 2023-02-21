package es.ruymi.repositories.empleado

import es.ruymi.db.getDepartamentos
import es.ruymi.db.getEmpleados
import es.ruymi.models.Departamento
import es.ruymi.models.Empleado
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("EmpleadoRepositoryImpl")
class EmpleadoRepositoryImpl: EmpleadoRepository {
    private val db : MutableMap<UUID, Empleado> = mutableMapOf()
    init {
        getEmpleados().forEach{
            db[it.id] = it
        }
    }
    override suspend fun findAll(): Flow<Empleado> {
        return db.values.toList().asFlow()
    }

    override suspend fun findById(id: UUID): Empleado? = withContext(Dispatchers.IO) {
        return@withContext db[id]
    }

    override suspend fun insert(entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        db[entity.id] = entity
        return@withContext entity
    }

    override suspend fun update(entity: Empleado): Empleado? = withContext(Dispatchers.IO) {
        db[entity.id]?.let {
            db.replace(entity.id, entity)
            return@let entity
        }
        return@withContext null
    }

    override suspend fun delete(entity: Empleado): Boolean = withContext(Dispatchers.IO) {
        db.remove(entity.id)?.let {
            return@let true
        }
        return@withContext false
    }
}