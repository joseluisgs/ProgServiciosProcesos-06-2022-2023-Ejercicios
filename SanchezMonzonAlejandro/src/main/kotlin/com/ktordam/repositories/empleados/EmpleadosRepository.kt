package com.ktordam.repositories.empleados

import com.ktordam.db.getEmpleadosInit
import com.ktordam.models.Empleado
import com.ktordam.repositories.CRUDRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger {}

@Single
@Named("EmpleadosRepository")
class EmpleadosRepository: CRUDRepository<Empleado, Int> {
    private val empleados: MutableMap<Int, Empleado> = mutableMapOf()

    /**
     * Inicializamos el repositorio añadiendo los datos ficticios.
     */
    init {
        logger.debug { "Iniciando Repositorio de Empleados" }

        getEmpleadosInit().forEach {
            empleados[it.id] = it
        }
    }

    /**
     * Función para listar todos los empleados.
     */
    override suspend fun findAll(): Flow<Empleado> {
        logger.debug { "findAll() - Buscando a todos los empleados." }

        return empleados.values.toList().asFlow()
    }

    /**
     * Función para encontrar un empleado en base a su UUID.
     *
     * @param id El UUID que lo identifica.
     */
    override suspend fun findByUUID(id: Int): Empleado? = withContext(Dispatchers.IO) {
        logger.debug { "findById(uuid) - Buscando a un empleado con uuid: $id." }

        return@withContext empleados[id]
    }

    /**
     * Función para guardar un empleado.
     *
     * @param entity Empleado a crear.
     */
    override suspend fun save(entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        logger.debug { "save(entity) - Salvando un empleado." }

        empleados[entity.id] = entity
        return@withContext entity
    }

    /**
     * Función para actualizar un empleado.
     *
     * @param id Identificador del empleado.
     * @param entity Empleado a actualizar.
     */
    override suspend fun update(id: Int, entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        logger.debug { "update(uuid, entity) - Actualizando al empleado con el uuid: $id.)" }

        val empleado = entity.copy(
            id = entity.id,
            nombre = entity.nombre,
            email = entity.email,
            avatar = entity.avatar,
            departamento = entity.departamento)

        empleados[id] = empleado
        return@withContext empleado
    }

    /**
     * Función para eliminar un empleado en base a su id.
     *
     * @param id El id del empleado.
     */
    override suspend fun delete(id: Int): Empleado? = withContext(Dispatchers.IO) {
        logger.debug { "delete(uuid) - Borrando el empleado con el uuid: $id. " }

        return@withContext empleados.remove(id)
    }
}