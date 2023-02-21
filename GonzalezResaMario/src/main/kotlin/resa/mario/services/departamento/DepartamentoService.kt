package resa.mario.services.departamento

import kotlinx.coroutines.flow.Flow
import resa.mario.models.Departamento
import java.util.UUID

interface DepartamentoService {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: UUID): Departamento
    suspend fun save(entity: Departamento): Departamento
    suspend fun update(id: UUID, entity: Departamento): Departamento
    suspend fun delete(entity: Departamento): Departamento
}