package mendoza.js.repositories.departamento

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import mendoza.js.entities.DepartamentoTable
import mendoza.js.mappers.toEntity
import mendoza.js.mappers.toModel
import mendoza.js.models.Departamento
import mendoza.js.service.database.DataBaseService
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

private val logger = KotlinLogging.logger { }

@Single
@Named("DepartamentoRepository")
class DepartamentoRepositoryImpl(
    private val dataBaseService: DataBaseService
) : DepartamentoRepository {

    init {
        logger.debug { "Iniciando repositorio de departamentos" }
    }

    override suspend fun findByNombre(nombre: String): Flow<Departamento> = withContext(Dispatchers.IO) {
        logger.debug { "findByNombre: Buscando departamento por el nombre $nombre" }
        return@withContext (dataBaseService.client selectFrom DepartamentoTable)
            .fetchAll()
            .filter { it.nombre.lowercase().contains(nombre.lowercase()) }
            .map { it.toModel() }
    }

    override suspend fun findAll(): Flow<Departamento> = withContext(Dispatchers.IO) {
        logger.debug { "findAll: Buscando todos los departamentos" }
        return@withContext (dataBaseService.client selectFrom DepartamentoTable).fetchAll().map { it.toModel() }
    }

    override suspend fun findById(id: UUID): Departamento? = withContext(Dispatchers.IO) {
        logger.debug { "findById: Buscando departamento con id $id" }
        return@withContext (dataBaseService.client selectFrom DepartamentoTable where DepartamentoTable.id eq id).fetchFirstOrNull()
            ?.toModel()
    }

    override suspend fun save(entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        logger.debug { "save: Guardando departamento $entity" }
        return@withContext (dataBaseService.client insertAndReturn entity.toEntity()).toModel()
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento? = withContext(Dispatchers.IO) {
        logger.debug { "update: Actualizando departamento $entity" }
        entity.let {
            val updateEntity = entity.toEntity()
            val res = (dataBaseService.client update DepartamentoTable
                    set DepartamentoTable.nombre eq updateEntity.nombre
                    set DepartamentoTable.presupuesto eq updateEntity.presupuesto
                    where DepartamentoTable.id eq id)
                .execute()
            if (res > 0) {
                return@withContext entity
            } else {
                return@withContext null
            }
        }
    }

    override suspend fun delete(entity: Departamento): Departamento? = withContext(Dispatchers.IO) {
        logger.debug { "delete: Borrando departamento ${entity.nombre}" }
        entity.let {
            val res =
                (dataBaseService.client deleteFrom DepartamentoTable where DepartamentoTable.id eq it.id).execute()
            if (res > 0) {
                return@withContext entity
            } else {
                return@withContext null
            }
        }
    }
}