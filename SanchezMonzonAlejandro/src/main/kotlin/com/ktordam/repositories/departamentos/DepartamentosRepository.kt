package com.ktordam.repositories.departamentos

import com.ktordam.db.getDepartamentosInit
import com.ktordam.models.Departamento
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
@Named("DepartamentosRepository")
class DepartamentosRepository: CRUDRepository<Departamento, Int> {
    private val departamentos: MutableMap<Int, Departamento> = mutableMapOf()

    /**
     * Inicializamos el repositorio añadiendo los datos ficticios.
     */
    init {
        logger.debug { "Iniciando Repositorio de Departamentos" }

        getDepartamentosInit().forEach {
            departamentos[it.id] = it
        }
    }

    /**
     * Función para listar todos los departamentos.
     */
    override suspend fun findAll(): Flow<Departamento> {
        logger.debug { "findAll() - Buscando a todos los departamentos." }

        return departamentos.values.toList().asFlow()
    }

    /**
     * Función para encontrar un departamento en base a su UUID.
     *
     * @param id El UUID que lo identifica.
     */
    override suspend fun findByUUID(id: Int): Departamento? = withContext(Dispatchers.IO) {
        logger.debug { "findById(uuid) - Buscando a un departamento con uuid: $id." }

        return@withContext departamentos[id]
    }

    /**
     * Función para crear un departamento.
     *
     * @param entity Departamento a crear.
     */
    override suspend fun save(entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        logger.debug { "save(entity) - Salvando un departamento." }

        departamentos[entity.id] = entity
        return@withContext entity
    }

    /**
     * Función para actualizar un departamento.
     *
     * @param id Identificador del departamento.
     * @param entity Departamento a actualizar.
     */
    override suspend fun update(id: Int, entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        logger.debug { "update(uuid, entity) - Actualizando al departamento con el uuid: $id.)" }

        val departamento = entity.copy(
            id = entity.id,
            nombre = entity.nombre,
            presupuesto = entity.presupuesto)

        departamentos[id] = departamento
        return@withContext departamento
    }

    /**
     * Función para eliminar un departamento en base a su id.
     *
     * @param id El id del departamento.
     */
    override suspend fun delete(id: Int): Departamento? = withContext(Dispatchers.IO) {
       logger.debug { "delete(uuid) - Borrando el departamento con el uuid: $id. " }

        return@withContext departamentos.remove(id)
    }
}
