package rodriguez.daniel.repositories.empleado

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import rodriguez.daniel.model.Empleado
import rodriguez.daniel.services.cache.empleado.IEmpleadoCache
import java.util.UUID

@Single
@Named("EmpleadoRepositoryCached")
class EmpleadoRepositoryCached(
    @Named("EmpleadoRepository")
    private val repo: IEmpleadoRepository,
    private val cache: IEmpleadoCache
): IEmpleadoRepository {
    override suspend fun findAll(): Flow<Empleado> = withContext(Dispatchers.IO) {
        repo.findAll()
    }

    override suspend fun findById(id: UUID): Empleado? = withContext(Dispatchers.IO) {
        var result: Empleado? = null

        cache.cache.asMap().forEach { if(it.key == id) result = it.value }
        if (result != null) {
            cache.cache.put(id, result!!)
            return@withContext result
        }

        result = repo.findById(id)
        if (result != null) cache.cache.put(id, result!!)
        result
    }

    override suspend fun save(entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        cache.cache.put(entity.id, entity)
        repo.save(entity)
    }

    override suspend fun delete(id: UUID): Empleado? = withContext(Dispatchers.IO) {
        val entity = repo.delete(id)
        if (entity != null) cache.cache.invalidate(id)
        entity
    }
}