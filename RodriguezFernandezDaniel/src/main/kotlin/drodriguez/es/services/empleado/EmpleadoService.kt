package drodriguez.es.services.empleado

import drodriguez.es.models.Empleado
import drodriguez.es.repositories.empleado.EmpleadoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
class EmpleadoService(
    @Named("EmpleadoRepository")
    private val empleadoService: EmpleadoRepository,
): IEmpleadoService {
    override suspend fun findAll(): Flow<Empleado> {
        return empleadoService.findAll()
    }

    override suspend fun findById(id: UUID): Empleado {
        return empleadoService.findById(id) ?: throw Exception("Empleado not found")
    }

    override suspend fun save(entity: Empleado): Empleado {
        return empleadoService.save(entity)
    }

    override suspend fun update(id: UUID, entity: Empleado): Empleado {
        return empleadoService.update(id, entity)!!
    }

    override suspend fun delete(entity: Empleado): Empleado {
        val check = empleadoService.findById(entity.id)
        check?.let {
            return empleadoService.delete(entity)!!
        } ?: throw Exception("Empleado not found")
    }
}