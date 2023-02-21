package mendoza.js.repositories.departamento

import kotlinx.coroutines.flow.Flow
import mendoza.js.models.Departamento
import mendoza.js.repositories.CrudRepository
import java.util.UUID

interface DepartamentoRepository : CrudRepository<Departamento, UUID> {
    suspend fun findByNombre(nombre: String): Flow<Departamento>
}