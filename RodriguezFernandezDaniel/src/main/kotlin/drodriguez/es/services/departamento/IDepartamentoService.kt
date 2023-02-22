package drodriguez.es.services.departamento

import drodriguez.es.models.Departamento
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IDepartamentoService {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: UUID): Departamento
    suspend fun save(entity: Departamento): Departamento
    suspend fun update(id: UUID, entity: Departamento): Departamento
    suspend fun delete(entity: Departamento): Departamento
}