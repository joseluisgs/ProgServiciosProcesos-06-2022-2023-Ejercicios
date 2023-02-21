package mendoza.js.repositories.empleado

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import mendoza.js.entities.EmpleadoTable
import mendoza.js.mappers.toEntity
import mendoza.js.mappers.toModel
import mendoza.js.models.Empleado
import mendoza.js.service.database.DataBaseService
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

private val logger = KotlinLogging.logger { }

@Single
@Named("EmpleadoRepository")
class EmpleadoRepositoryImpl(
    private val dataBaseService: DataBaseService
) : EmpleadoRepository {

    init {
        logger.debug { "Iniciando repositorio de departamentos" }
    }

    override suspend fun findByNombre(nombre: String): Flow<Empleado> = withContext(Dispatchers.IO) {
        logger.debug { "findByNombre: Buscando empleado por el nombre $nombre" }
        return@withContext (dataBaseService.client selectFrom EmpleadoTable)
            .fetchAll()
            .filter { it.nombre.lowercase().contains(nombre.lowercase()) }
            .map { it.toModel() }
    }

    override suspend fun findAll(): Flow<Empleado> = withContext(Dispatchers.IO) {
        logger.debug { "findAll: Buscando todos los empleados" }
        return@withContext (dataBaseService.client selectFrom EmpleadoTable)
            .fetchAll().map { it.toModel() }
    }

    override suspend fun findById(id: UUID): Empleado? = withContext(Dispatchers.IO) {
        logger.debug { "findById: Buscando empleado con id $id" }
        return@withContext (dataBaseService.client selectFrom EmpleadoTable where EmpleadoTable.id eq id).fetchFirstOrNull()
            ?.toModel()
    }

    override suspend fun save(entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        logger.debug { "save: Guardando empleado $entity" }
        return@withContext (dataBaseService.client insertAndReturn entity.toEntity()).toModel()
    }

    override suspend fun update(id: UUID, entity: Empleado): Empleado? = withContext(Dispatchers.IO) {
        logger.debug { "update: Actualizando empleado $entity" }
        entity.let {
            val updateEntity = entity.toEntity()
            val res = (dataBaseService.client update EmpleadoTable
                    set EmpleadoTable.nombre eq updateEntity.nombre
                    set EmpleadoTable.salario eq updateEntity.salario
                    where EmpleadoTable.id eq id)
                .execute()
            if (res > 0) {
                return@withContext entity
            } else {
                return@withContext null
            }
        }
    }

    override suspend fun delete(entity: Empleado): Empleado? = withContext(Dispatchers.IO) {
        logger.debug { "delete: Borrando empleado ${entity.nombre}" }
        entity.let {
            val res = (dataBaseService.client deleteFrom EmpleadoTable where EmpleadoTable.id eq it.id).execute()
            if (res > 0) {
                return@withContext entity
            } else {
                return@withContext null
            }
        }
    }
}