package resa.mario.repositories.usuario

import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Usuario
import resa.mario.services.DataBaseService
import java.util.*

private val log = KotlinLogging.logger {}

/**
 * Repositorio de usuarios que trabaja con la base de datos
 *
 * @property dataBaseService
 */
@Single
@Named("UsuarioRepository")
class UsuarioRepositoryImpl(
    private val dataBaseService: DataBaseService
) : UsuarioRepository {

    override suspend fun findByName(username: String): Usuario? {
        log.info { "Buscando usuario con nombre: $username" }

        var user: Usuario? = null

        dataBaseService.getTables().tableUsuarios.values.forEach {
            if (it.username == username) user = it
        }

        return user
    }

    override suspend fun login(username: String, password: String): Usuario? {
        log.info { "Inicio de sesion del usuario: $username" }

        val user = findByName(username) ?: return null

        user.let {
            if (Bcrypt.verify(password, user.password.encodeToByteArray())) {
                return user
            } else return null
        }
    }

    override suspend fun findAll(): Flow<Usuario> {
        log.info { "Obteniendo todos los usuarios" }

        return dataBaseService.getTables().tableUsuarios.values.asFlow()
    }

    override suspend fun findById(id: UUID): Usuario? {
        log.info { "Buscando usuario con id: $id" }

        return dataBaseService.getTables().tableUsuarios[id]
    }

    override suspend fun save(entity: Usuario): Usuario {
        log.info { "Almacenando usuario: ${entity.username}" }

        dataBaseService.getTables().tableUsuarios[entity.id] = entity

        return dataBaseService.getTables().tableUsuarios[entity.id]!!
    }

    override suspend fun update(id: UUID, entity: Usuario): Usuario? {
        log.info { "Actualizando usuario con id: $id" }

        val user = findById(id) ?: return null

        // Para evitar el cambio de id
        val userCopy = entity.copy(
            id = user.id,
            username = entity.username,
            password = entity.password,
            role = entity.role
        )

        user.let {
            dataBaseService.getTables().tableUsuarios.replace(id, userCopy)
        }

        return userCopy
    }

    override suspend fun delete(entity: Usuario): Usuario? {
        log.info { "Eliminando usuario con id: ${entity.id}" }

        val user = findById(entity.id) ?: return null
        dataBaseService.getTables().tableUsuarios.remove(user.id)

        return user
    }
}