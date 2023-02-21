package rodriguez.daniel.repositories.departamento

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import rodriguez.daniel.model.Departamento
import java.util.UUID

@Single
@Named("DepartamentoRepository")
class DepartamentoRepository: IDepartamentoRepository {
    private val db = mutableMapOf<UUID, Departamento>()

    override suspend fun findAll(): Flow<Departamento> = withContext(Dispatchers.IO) {
        db.values.asFlow()
    }

    override suspend fun findById(id: UUID): Departamento? = withContext(Dispatchers.IO) {
        db[id]
    }

    override suspend fun save(entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        db[entity.id] = entity
        entity
    }

    override suspend fun delete(id: UUID): Departamento? = withContext(Dispatchers.IO) {
        db.remove(id)
    }
}