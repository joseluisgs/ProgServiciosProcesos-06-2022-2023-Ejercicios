package resa.mario.repositories.empleado

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Empleado
import resa.mario.services.DataBaseService
import java.util.*

private val log = KotlinLogging.logger {}

/**
 * Repositorio de empleados que trabaja con la base de datos
 *
 * @property dataBaseService
 */
@Single
@Named("EmpleadoRepository")
class EmpleadoRepositoryImpl(
    private val dataBaseService: DataBaseService
) : EmpleadoRepository {

    override suspend fun findAll(): Flow<Empleado> {
        log.info { "Obteniendo todos los empleados" }

        return dataBaseService.getTables().tableEmpleados.values.asFlow()
    }

    override suspend fun findById(id: UUID): Empleado? {
        log.info { "Buscando empleado con id: $id" }

        return dataBaseService.getTables().tableEmpleados[id]
    }

    override suspend fun save(entity: Empleado): Empleado {
        log.info { "Almacenando usuario ${entity.nombre}" }

        dataBaseService.getTables().tableEmpleados[entity.id] = entity

        return dataBaseService.getTables().tableEmpleados[entity.id]!!
    }

    override suspend fun update(id: UUID, entity: Empleado): Empleado? {
        log.info { "Actualizando empleado con id: $id" }

        val empleado = findById(id) ?: return null

        // Para evitar el cambio de id
        val empleadoCopy = entity.copy(
            id = empleado.id,
            nombre = entity.nombre,
            email = entity.email,
            avatar = entity.avatar,
            departamentoId = entity.id
        )

        empleado.let {
            dataBaseService.getTables().tableEmpleados.replace(id, empleadoCopy)
        }

        return empleadoCopy
    }

    override suspend fun delete(entity: Empleado): Empleado? {
        log.info { "Eliminando empleado con id: ${entity.id}" }

        val empleado = findById(entity.id) ?: return null
        dataBaseService.getTables().tableEmpleados.remove(empleado.id)

        return empleado
    }

}