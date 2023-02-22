package drodriguez.es.repositories.empleado

import drodriguez.es.models.Empleado
import drodriguez.es.services.DBServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*
@Single
@Named("EmpleadoRepository")
class EmpleadoRepository(
    private val dbServices: DBServices
): IEmpleadoRepository {
    override suspend fun findAll(): Flow<Empleado> {
        return dbServices.getLists().listEmpleados.values.asFlow()
    }

    override suspend fun findById(id: UUID): Empleado? {
        return dbServices.getLists().listEmpleados[id]
    }

    override suspend fun save(entity: Empleado): Empleado {
        dbServices.getLists().listEmpleados[entity.id] = entity
        return dbServices.getLists().listEmpleados[entity.id]!!
    }

    override suspend fun update(id: UUID, entity: Empleado): Empleado? {
        val empleado = findById(id)?: return null
        val empleadoCC = entity.copy(
            id = empleado.id,
            nombre = entity.nombre,
            email = entity.email,
            avatar = entity.avatar,
            departamentoId = entity.id
        )
        empleado.let { dbServices.getLists().listEmpleados.replace(id, empleadoCC) }
        return empleadoCC
    }

    override suspend fun delete(entity: Empleado): Empleado? {
        val empleado = findById(entity.id)?: return null
        dbServices.getLists().listEmpleados.remove(empleado.id)
        return empleado
    }
}