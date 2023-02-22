package sanchez.mireya.services.empleado

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import sanchez.mireya.models.Empleado
import sanchez.mireya.repositories.empleado.EmpleadosRepository

@Single
class EmpleadosServiceImpl(
    @Named("EmpleadoRepository")
     private val repository: EmpleadosRepository
     ): EmpleadosService {
    override suspend fun findAll(): Flow<Empleado> {
        return repository.findAll()
    }

    override suspend fun findById(id: Int): Empleado {
        return repository.findById(id) ?: throw Exception("Does not exist.")
    }

    override suspend fun save(entity: Empleado): Empleado {
        return repository.save(entity)
    }

    override suspend fun update(id: Int, entity: Empleado): Empleado {
        val existe = repository.findById(id)

        existe?.let {
            return repository.update(id, entity)
        } ?: throw Exception("Not found")
    }

    override suspend fun delete(id: Int): Empleado {
        val existe = repository.findById(id)

        existe?.let {
            return repository.delete(id)!!
        } ?: throw Exception("Not found")
    }
}