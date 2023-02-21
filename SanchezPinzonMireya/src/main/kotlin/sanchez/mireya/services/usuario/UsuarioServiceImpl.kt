package sanchez.mireya.services.usuario

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import sanchez.mireya.models.Usuario
import sanchez.mireya.repositories.usuario.UsuariosRepository
import java.util.*

@Single
class UsuarioServiceImpl(
    @Named("UsuariosRepository")
    private val repository: UsuariosRepository
): UsuarioService {
    override suspend fun findAll(): Flow<Usuario> {
        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Usuario {
        return repository.findById(id) ?: throw Exception("Does not exist")
    }

    override suspend fun login(username: String, password: String): Usuario? {
        return repository.login(username, password)
    }

    override suspend fun save(entity: Usuario): Usuario {
        return repository.save(entity)
    }

    override suspend fun update(id: UUID, entity: Usuario): Usuario {
        val existe = repository.findById(id)

        existe?.let {
            return repository.update(id, entity)!!
        } ?: throw Exception("Not found")
    }

    override suspend fun delete(entity: Usuario): Usuario {
        val existe = repository.findById(entity.id!!)

        existe?.let {
            return repository.delete(existe.id)!!
        } ?: throw Exception("Not found")
    }
}