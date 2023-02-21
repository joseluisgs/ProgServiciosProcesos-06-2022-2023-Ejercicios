package resa.mario.repositories.departamento

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Departamento
import resa.mario.services.DataBaseService
import java.util.*

private val log = KotlinLogging.logger {}

/**
 * Repositorio de departamentos que trabaja con la base de datos
 *
 * @property dataBaseService
 */
@Single
@Named("DepartamentoRepository")
class DepartamentoRepositoryImpl(
    private val dataBaseService: DataBaseService
) : DepartamentoRepository {

    override suspend fun findAll(): Flow<Departamento> {
        log.info { "Obteniendo a todos los departamentos" }

        return dataBaseService.getTables().tableDepartamentos.values.asFlow()
    }

    override suspend fun findById(id: UUID): Departamento? {
        log.info { "Buscando departamento con id: $id" }

        return dataBaseService.getTables().tableDepartamentos[id]
    }

    override suspend fun save(entity: Departamento): Departamento {
        log.info { "Almacenando departamento ${entity.nombre}" }

        dataBaseService.getTables().tableDepartamentos[entity.id] = entity

        return dataBaseService.getTables().tableDepartamentos[entity.id]!!
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento? {
        log.info { "Actualizando departamento con id: $id" }

        val departamento = findById(id) ?: return null

        // Para evitar el cambio de id
        val departamentoCopy = entity.copy(
            id = departamento.id,
            nombre = entity.nombre,
            presupuesto = entity.presupuesto
        )

        departamento.let {
            dataBaseService.getTables().tableDepartamentos.replace(id, departamentoCopy)
        }

        return departamentoCopy
    }

    override suspend fun delete(entity: Departamento): Departamento? {
        log.info { "Eliminando departamento con id: ${entity.id}" }

        val departamento = findById(entity.id) ?: return null
        dataBaseService.getTables().tableDepartamentos.remove(entity.id)

        return departamento
    }
}