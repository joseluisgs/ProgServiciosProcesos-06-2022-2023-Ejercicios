package drodriguez.es.services.departamento

import drodriguez.es.models.Departamento
import drodriguez.es.repositories.departamento.DepartamentoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
class DepartamentoService(
    @Named("DepartamentoRepository")
    private val departamentoRepository: DepartamentoRepository
): IDepartamentoService {
    override suspend fun findAll(): Flow<Departamento> {
        return departamentoRepository.findAll()
    }

    override suspend fun findById(id: UUID): Departamento {
        return departamentoRepository.findById(id) ?: throw Exception("No se encontró el departamento")
    }

    override suspend fun save(entity: Departamento): Departamento {
        return departamentoRepository.save(entity)
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento {
        return departamentoRepository.update(id, entity)!!
    }

    override suspend fun delete(entity: Departamento): Departamento {
        return departamentoRepository.delete(entity)!!
    }
}