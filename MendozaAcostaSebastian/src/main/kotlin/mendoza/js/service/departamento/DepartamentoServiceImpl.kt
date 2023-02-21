package mendoza.js.service.departamento

import io.ktor.server.engine.*
import kotlinx.coroutines.flow.Flow
import mendoza.js.exceptions.DepartamentoNotFoundException
import mendoza.js.models.Departamento
import mendoza.js.repositories.departamento.DepartamentoRepository
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

private val logger = KotlinLogging.logger { }

@Single
class DepartamentoServiceImpl(
    @Named("DepartamentoCachedRepository")
    private val repository: DepartamentoRepository
) : DepartamentoService {

    init {
        logger.debug { "Iniciando el servicio de departamento" }
    }

    override suspend fun findAll(): Flow<Departamento> {
        logger.debug { "findAll: Buscando todos los departamentos en servicio" }
        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Departamento? {
        logger.debug { "findById: Buscando departamento en servicio por id $id" }
        return repository.findById(id)
    }

    override suspend fun findByNombre(nombre: String): Flow<Departamento> {
        logger.debug { "findByNombre: Buscando departamento en servicio con nombre $nombre" }
        return repository.findByNombre(nombre)
    }


    override suspend fun save(entity: Departamento): Departamento {
        logger.debug { "save: Creando departamento en servicio" }
        return repository.save(entity)
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento {
        logger.debug { "update: Actualizando departamento en servicio con id $id" }
        val existe = repository.findById(id)
        existe?.let {
            return repository.update(id, entity)!!
        } ?: throw DepartamentoNotFoundException("No se ha encontrado el departamento con id $id")
    }

    override suspend fun delete(id: UUID): Departamento? {
        logger.debug { "delete: Borrando departamento en servicio con id $id" }
        val dpt = repository.findById(id)
        dpt?.let {
            repository.delete(it)
        }
        return dpt
    }
}