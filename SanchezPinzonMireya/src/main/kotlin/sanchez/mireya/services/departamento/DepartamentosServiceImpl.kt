package sanchez.mireya.services.departamento

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import sanchez.mireya.models.Departamento
import sanchez.mireya.repositories.departamento.DepartamentosRepository
import sanchez.mireya.repositories.empleado.EmpleadosRepository
import sanchez.mireya.services.CacheService
import java.util.*

@Single
class DepartamentosServiceImpl(
    @Named("DepartamentoRepository")
    private val repository: DepartamentosRepository,
    @Named("EmpleadoRepository")
    private val empleadoRepository: EmpleadosRepository,
    private val cacheService: CacheService
) : DepartamentosService {

    override suspend fun findAll(): Flow<Departamento> {
        return repository.findAll()
    }

    override suspend fun findById(id: Int): Departamento {
        val existe = cacheService.cache.get(id)
        existe?.let {
            return it
        } ?: run {
            return repository.findById(id) ?: throw Exception("Does not exist")
        }
    }

    override suspend fun save(entity: Departamento): Departamento {
        val prueba = cacheService.cache.put(entity.id, entity)
        return repository.save(entity)
    }

    override suspend fun update(id: Int, entity: Departamento): Departamento {
        val existe = repository.findById(id)

        existe?.let {
            cacheService.cache.put(id, entity)
            return repository.update(id, entity)
        } ?: throw Exception("Not found")
    }

    override suspend fun delete(id: Int): Departamento {
        val existe = repository.findById(id)

        existe?.let {
            val empleados = empleadoRepository.findAll().toList().filter { it.departament?.id == existe.id }
            val count = empleados.size

            System.err.println(empleados.size)
            if (count == 0) {
                return repository.delete(id)!!
            } else throw Exception("Cannot delete, $count workers already assigned.")
        } ?: throw Exception("Not found")
    }
}