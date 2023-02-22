package sanchez.mireya.repositories.empleado

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import sanchez.mireya.db.getEmpleados
import sanchez.mireya.models.Empleado

val logger = KotlinLogging.logger {}


@Single
@Named("EmpleadoRepository")
class EmpleadosRepository : IEmpleadosRepository {

    private val empleados: MutableMap<Int, Empleado> = mutableMapOf()

    init {
        logger.info { "Iniciando repositorio empleados" }

        getEmpleados().forEach {
            empleados[it.id] = it
        }
    }

    override suspend fun findAll(): Flow<Empleado> {
        logger.info { "findAll()" }

        return empleados.values.toList().asFlow()
    }

    override suspend fun findById(id: Int): Empleado? {
        logger.info { "findById(): $id" }

        return empleados[id]
    }

    override suspend fun save(entity: Empleado): Empleado {
        logger.info { "save(): $entity" }

        empleados[entity.id] = entity
        return entity
    }

    override suspend fun update(id: Int, entity: Empleado): Empleado {
        logger.info { "update(): ${entity.id}" }

        empleados[id] = entity

        return entity
    }

    override suspend fun delete(id: Int): Empleado? {
        logger.info { "delete(): $id" }

        return empleados.remove(id)
    }


}