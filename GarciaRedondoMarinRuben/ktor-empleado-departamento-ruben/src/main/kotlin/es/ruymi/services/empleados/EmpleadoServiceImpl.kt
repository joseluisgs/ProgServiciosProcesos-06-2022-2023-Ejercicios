package es.ruymi.services.empleados

import es.ruymi.exceptions.UserBadRequestException
import es.ruymi.exceptions.UserNotFoundException
import es.ruymi.models.Departamento
import es.ruymi.models.Empleado
import es.ruymi.repositories.empleado.EmpleadoRepository
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

private val logger = KotlinLogging.logger {}

@Single
class EmpleadoServiceImpl(
    @Named("EmpleadoRepositoryImpl")
    private val repository: EmpleadoRepository
) : EmpleadoService {

    init {
        logger.debug { "Inicializando el servicio de Empleados" }
    }

    override suspend fun findAll(): Flow<Empleado> {
        logger.debug { "findAll: Buscando todos los Empleados" }

        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Empleado {
        logger.debug { "findById: Buscando Empleado con id: $id" }

        return repository.findById(id) ?: throw UserNotFoundException("No se ha encontrado el Empleado con id: $id")
    }

    override suspend fun save(entity: Empleado): Empleado {
        logger.debug { "insert: Creando Empleado" }

        val departamento = repository.findById(entity.id)
        if (departamento != null) {
            throw UserBadRequestException("Ya existe un Empleado con id: ${entity.id}")
        }

        return repository.insert(entity)
    }

    override suspend fun update(entity: Empleado): Empleado {
        logger.debug { "update: Actualizando Empleado con id: ${entity.id}" }

        return repository.update(entity)!!

    }

    override suspend fun delete(id: UUID): Empleado? {
        logger.debug { "delete: Borrando Empleado con id: $id" }

        val user = repository.findById(id)
        user?.let {
            repository.delete(it)
        }
        return user
    }
}