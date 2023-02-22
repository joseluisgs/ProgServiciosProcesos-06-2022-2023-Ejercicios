package es.ruymi.repositories.departamento

import es.ruymi.db.getDepartamentos
import es.ruymi.models.Departamento
import es.ruymi.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("DepartamentoRepositoryImpl")
class DepartamentoRepositoryImpl: DepartamentoRepository {
    private val db : MutableMap<UUID, Departamento> = mutableMapOf()

    init {
        getDepartamentos().forEach{
            db[it.id] = it
        }
    }
    override suspend fun findAll(): Flow<Departamento> {
        return db.values.toList().asFlow()
    }

    override suspend fun findById(id: UUID): Departamento? {
        db[id]?.let {
            return it
        }
        return null
    }

    override suspend fun insert(entity: Departamento): Departamento {
        db[entity.id] = entity
        return entity
    }

    override suspend fun update(entity: Departamento): Departamento? {
        db[entity.id]?.let {
            db.replace(entity.id, entity)
            return entity
        }
        return null
    }

    override suspend fun delete(entity: Departamento): Boolean {
        db.remove(entity.id)?.let {
            return true
        }
        return false
    }

    fun deleteAll(){
        db.clear()

    }
}