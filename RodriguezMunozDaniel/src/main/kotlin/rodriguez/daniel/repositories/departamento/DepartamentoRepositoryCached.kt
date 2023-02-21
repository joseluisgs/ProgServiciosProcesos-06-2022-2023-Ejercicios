package rodriguez.daniel.repositories.departamento

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import rodriguez.daniel.model.Departamento
import rodriguez.daniel.services.cache.departamento.IDepartamentoCache
import java.util.UUID

@Single
@Named("DepartamentoRepositoryCached")
class DepartamentoRepositoryCached(
    @Named("DepartamentoRepository")
    private val repo: IDepartamentoRepository,
    private val cache: IDepartamentoCache
): IDepartamentoRepository {
    override suspend fun findAll(): Flow<Departamento> = withContext(Dispatchers.IO) {
        repo.findAll()
    }

    override suspend fun findById(id: UUID): Departamento? = withContext(Dispatchers.IO) {
        var result: Departamento? = null

        cache.cache.asMap().forEach { if(it.key == id) result = it.value }
        if (result != null) {
            cache.cache.put(id, result!!)
            return@withContext result
        }

        result = repo.findById(id)
        if (result != null) cache.cache.put(id, result!!)
        result
    }

    override suspend fun save(entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        cache.cache.put(entity.id, entity)
        repo.save(entity)
    }

    override suspend fun delete(id: UUID): Departamento? = withContext(Dispatchers.IO) {
        val entity = repo.delete(id)
        if (entity != null) cache.cache.invalidate(id)
        entity
    }
}