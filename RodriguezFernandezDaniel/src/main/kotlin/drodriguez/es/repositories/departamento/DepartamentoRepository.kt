package drodriguez.es.repositories.departamento

import drodriguez.es.models.Departamento
import drodriguez.es.services.DBServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*
@Single
@Named("DepartamentoRepository")
class DepartamentoRepository(
    private val dbServices: DBServices
): IDepartamentoRepository {
    override suspend fun findAll(): Flow<Departamento> {
        return dbServices.getLists().listDepartamentos.values.asFlow()
    }

    override suspend fun findById(id: UUID): Departamento? {
        return dbServices.getLists().listDepartamentos[id]
    }

    override suspend fun save(entity: Departamento): Departamento {
        dbServices.getLists().listDepartamentos[entity.id] = entity
        return dbServices.getLists().listDepartamentos[entity.id]!!
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento? {
        val departamento = findById(id)?: return null
        val departamentoCC = departamento.copy(
            id = departamento.id,
            nombreDepartamento = departamento.nombreDepartamento
        )
        departamento.let {
            dbServices.getLists().listDepartamentos.replace(id, departamentoCC)
        }
        return departamentoCC
    }

    override suspend fun delete(entity: Departamento): Departamento? {
        val departamento = findById(entity.id)?: return null
        dbServices.getLists().listDepartamentos.remove(departamento.id)
        return departamento
    }
}