package mendoza.js.service.empleado

import kotlinx.coroutines.flow.Flow
import mendoza.js.models.Empleado
import mendoza.js.repositories.empleado.EmpleadoRepository
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

private val logger = KotlinLogging.logger { }

@Single
class EmpleadoServiceImpl(
    @Named("EmpleadoCachedRepository")
    private val empleadoRepository: EmpleadoRepository
) : EmpleadoService {

    init {
        logger.debug { "Iniciando el servicio de empleado" }
    }

    override suspend fun findAll(): Flow<Empleado> {
        logger.debug { "findAll: Buscando todos los empleados en servicio" }
        return empleadoRepository.findAll()
    }

    override suspend fun findById(id: UUID): Empleado? {
        logger.debug { "findById: Buscando empleado en servicio por id $id" }
        return empleadoRepository.findById(id)
    }

    override suspend fun findByNombre(nombre: String): Flow<Empleado> {
        logger.debug { "findByNombre: Buscando empleado en servicio con nombre $nombre" }
        return empleadoRepository.findByNombre(nombre)
    }

    override suspend fun save(entity: Empleado): Empleado {
        logger.debug { "save: Creando empleado en servicio" }
        return empleadoRepository.save(entity)
    }

    override suspend fun update(id: UUID, entity: Empleado): Empleado? {
        logger.debug { "update: Actualizando empleado en servicio con id $id" }
        val existe = empleadoRepository.findById(id)
        existe?.let {
            empleadoRepository.update(id, entity)
        }
        return existe
    }

    override suspend fun delete(id: UUID): Empleado? {
        logger.debug { "delete: Borrando empleado con id $id" }
        val emp = empleadoRepository.findById(id)
        emp?.let {
            empleadoRepository.delete(it)
        }
        return emp
    }
}