package es.ruymi.services.departamento

import es.ruymi.exceptions.UserBadRequestException
import es.ruymi.exceptions.UserNotFoundException
import es.ruymi.models.Departamento
import es.ruymi.repositories.departamento.DepartamentoRepository
import es.ruymi.services.empleados.EmpleadoService
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

private val logger = KotlinLogging.logger {}

@Single
class DepartamentoServiceImpl(
    @Named("DepartamentoRepositoryImpl")
    private val repository: DepartamentoRepository
) : DepartamentoService {

    init {
        logger.debug { "Inicializando el servicio de Departamentos" }
    }

    override suspend fun findAll(): Flow<Departamento> {
        logger.debug { "findAll: Buscando todos los usuarios" }

        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Departamento {
        logger.debug { "findById: Buscando departamento con id: $id" }

        return repository.findById(id) ?: throw UserNotFoundException("No se ha encontrado el departamento con id: $id")
    }

    override suspend fun save(entity: Departamento): Departamento {
        logger.debug { "insert: Creando departamento" }

        val departamento = repository.findById(entity.id)
        if (departamento != null) {
            throw UserBadRequestException("Ya existe un departamento con id: ${entity.id}")
        }

        return repository.insert(entity)
    }

    override suspend fun update(entity: Departamento): Departamento? {
        logger.debug { "update: Actualizando departamento con id: ${entity.id}" }

        return repository.update(entity)

    }

    override suspend fun delete(id: UUID): Departamento? {
        logger.debug { "delete: Borrando departamento con id: $id" }

        val user = repository.findById(id)
        user?.let {
            repository.delete(it)
        }
        return user
    }
}