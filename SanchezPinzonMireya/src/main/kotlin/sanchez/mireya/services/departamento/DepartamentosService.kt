package sanchez.mireya.services.departamento

import kotlinx.coroutines.flow.Flow
import sanchez.mireya.models.Departamento

interface DepartamentosService {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: Int): Departamento
    suspend fun save(entity: Departamento): Departamento
    suspend fun update(id: Int, entity: Departamento): Departamento
    suspend fun delete(id: Int): Departamento
}