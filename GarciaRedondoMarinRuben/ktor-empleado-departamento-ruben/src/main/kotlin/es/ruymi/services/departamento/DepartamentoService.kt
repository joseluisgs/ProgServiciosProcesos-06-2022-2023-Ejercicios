package es.ruymi.services.departamento

import es.ruymi.models.Departamento
import es.ruymi.models.User
import kotlinx.coroutines.flow.Flow
import java.util.*

interface DepartamentoService {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: UUID): Departamento?
    suspend fun save(entity: Departamento): Departamento
    suspend fun update(entity: Departamento): Departamento?
    suspend fun delete(id: UUID): Departamento?
}