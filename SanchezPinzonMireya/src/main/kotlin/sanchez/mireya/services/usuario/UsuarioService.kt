package sanchez.mireya.services.usuario

import kotlinx.coroutines.flow.Flow
import sanchez.mireya.models.Usuario
import java.util.*

interface UsuarioService {
    suspend fun findAll(): Flow<Usuario>
    suspend fun findById(id: UUID): Usuario
    suspend fun login(username: String, password: String): Usuario?
    suspend fun save(entity: Usuario): Usuario
    suspend fun update(id: UUID, entity: Usuario): Usuario
    suspend fun delete(entity: Usuario): Usuario
}