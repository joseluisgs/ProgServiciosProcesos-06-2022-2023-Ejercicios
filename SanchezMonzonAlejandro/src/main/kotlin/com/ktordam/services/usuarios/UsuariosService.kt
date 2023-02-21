package com.ktordam.services.usuarios

import com.ktordam.exceptions.UsuariosNotFoundException
import com.ktordam.models.Usuario
import com.ktordam.repositories.usuarios.UsuariosRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class UsuariosService(
    @Named("UsuariosRepository")
    private val usuariosRepository: UsuariosRepository
): IUsuariosService {

    override suspend fun findAll(): Flow<Usuario> {
        return usuariosRepository.findAll()
    }

    override suspend fun findByUUID(uuid: UUID): Usuario {
        return usuariosRepository.findByUUID(uuid) ?: throw UsuariosNotFoundException("Usuario con uuid: $uuid no encontrado.")
    }

    override suspend fun findByUserame(username: String): Usuario {
        return usuariosRepository.findByUserame(username) ?: throw UsuariosNotFoundException("Usuario con username: $username no encontrado.")
    }

    override suspend fun login(username: String, password: String): Usuario? {
        return usuariosRepository.login(username, password)
    }

    override suspend fun save(usuario: Usuario): Usuario {
        return usuariosRepository.save(usuario)
    }

    override suspend fun update(uuid: UUID, usuario: Usuario): Usuario {
        val existe = usuariosRepository.findByUUID(uuid)

        existe?.let {
            return usuariosRepository.update(uuid, usuario)
        } ?: throw UsuariosNotFoundException("Usuario con uuid: $uuid no encontrado.")
    }

    override suspend fun delete(uuid: UUID): Usuario? {
        val existe = usuariosRepository.findByUUID(uuid)

        existe?.let {
            return usuariosRepository.delete(uuid)
        } ?: throw UsuariosNotFoundException("Usuario con uuid: $uuid no encontrado.")
    }
}