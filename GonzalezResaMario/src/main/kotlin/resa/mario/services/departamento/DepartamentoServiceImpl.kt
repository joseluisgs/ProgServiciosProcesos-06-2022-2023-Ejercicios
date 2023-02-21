package resa.mario.services.departamento

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Departamento
import resa.mario.repositories.departamento.DepartamentoCachedRepositoryImpl
import resa.mario.repositories.empleado.EmpleadoCachedRepositoryImpl
import java.util.UUID

/**
 * Servicio de departamentos, se encarga de realizar las llamadas a los repositorios relacionados
 *
 * @property repository
 * @property empleadoRepository
 */
@Single
class DepartamentoServiceImpl(
    @Named("DepartamentoCachedRepository")
    private val repository: DepartamentoCachedRepositoryImpl,
    @Named("EmpleadoCachedRepository")
    private val empleadoRepository: EmpleadoCachedRepositoryImpl
) : DepartamentoService {

    override suspend fun findAll(): Flow<Departamento> {
        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Departamento {
        return repository.findById(id) ?: throw Exception("No existe ese departamento")
    }

    override suspend fun save(entity: Departamento): Departamento {
        return repository.save(entity)
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento {
        val existe = repository.findById(id)

        existe?.let {
            return repository.update(id, entity)
        } ?: throw Exception("No se encontro ese departamento")
    }

    override suspend fun delete(entity: Departamento): Departamento {
        val existe = repository.findById(entity.id)

        existe?.let {
            val empleados = empleadoRepository.findAll().toList().filter { it.departamentoId == existe.id }
            val count = empleados.size

            if (count == 0) {
                return repository.delete(existe)!!
            } else throw Exception("No fue posible eliminar el departamento | $count empleados")
        } ?: throw Exception("No se encontro ese departamento")
    }
}