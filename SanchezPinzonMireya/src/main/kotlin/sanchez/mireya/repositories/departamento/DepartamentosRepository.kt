package sanchez.mireya.repositories.departamento

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import sanchez.mireya.db.getDepartamentos
import sanchez.mireya.models.Departamento

val logger = KotlinLogging.logger {}

@Single
@Named("DepartamentoRepository")
class DepartamentosRepository : IDepartamentoRepository {
    private val departamentos: MutableMap<Int, Departamento> = mutableMapOf()

    init {
        logger.info { "Iniciando repositorio departamentos" }

        getDepartamentos().forEach {
            departamentos[it.id] = it
        }
    }

    override suspend fun findAll(): Flow<Departamento> {
        logger.info { "findAll()" }

        return departamentos.values.toList().asFlow()
    }

    override suspend fun findById(id: Int): Departamento? = withContext(Dispatchers.IO) {
        logger.info { "findById(): $id" }

        return@withContext departamentos[id]
    }

    override suspend fun save(entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        logger.info { "save(): $entity" }

        departamentos[entity.id] = entity
        return@withContext entity
    }

    override suspend fun update(id: Int, entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        logger.info { "update(): ${entity.id}" }

        departamentos[id] = entity
        return@withContext entity
    }

    override suspend fun delete(id: Int): Departamento? = withContext(Dispatchers.IO) {
        logger.info { "delete(): $id" }

        return@withContext departamentos.remove(id)
    }
}