package resa.mario.services.usuario

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Usuario
import resa.mario.repositories.usuario.UsuarioRepositoryImpl
import java.util.*

/**
 * Servicio de usuarios, se encarga de realizar las llamadas al repositorio relacionado
 *
 * @property repository
 */
@Single
class UsuarioServiceImpl(
    @Named("UsuarioRepository")
    private val repository: UsuarioRepositoryImpl
) : UsuarioService {
    override suspend fun findAll(): Flow<Usuario> {
        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Usuario {
        return repository.findById(id) ?: throw Exception("No existe ese usuario")
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
        } ?: throw Exception("No se encontro ese usuario")
    }

    override suspend fun delete(entity: Usuario): Usuario {
        val existe = repository.findById(entity.id)

        existe?.let {
            return repository.delete(existe)!!
        } ?: throw Exception("No se encontro ese usuario")
    }
}