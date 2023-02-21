package mendoza.js.service.departamento

import kotlinx.coroutines.flow.Flow
import mendoza.js.models.Departamento
import java.util.*

interface DepartamentoService {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: UUID): Departamento?
    suspend fun findByNombre(nombre: String): Flow<Departamento>
    suspend fun save(entity: Departamento): Departamento
    suspend fun update(id: UUID, entity: Departamento): Departamento
    suspend fun delete(id: UUID): Departamento?
}