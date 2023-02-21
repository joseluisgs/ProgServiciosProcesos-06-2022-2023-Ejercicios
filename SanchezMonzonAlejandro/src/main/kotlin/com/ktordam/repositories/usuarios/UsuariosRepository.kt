package com.ktordam.repositories.usuarios

import com.ktordam.db.getUsuariosInit
import com.ktordam.models.Usuario
import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Single
@Named("UsuariosRepository")
class UsuariosRepository: IUsuariosRepository {
    private val usuarios: MutableMap<UUID, Usuario> = mutableMapOf()

    /**
     * Inicializamos el repositorio añadiendo los datos ficticios.
     */
    init {
        logger.debug { "Iniciando Repositorio de Usuarios" }

        getUsuariosInit().forEach {
            usuarios[it.uuid] = it
        }
    }

    /**
     * Función para encontrar un usuario en base a su username.
     *
     * @param username El username que lo identifica.
     */
    override suspend fun findByUserame(username: String): Usuario? {
        logger.debug { "findByUsername($username) - Buscando a un usuario con username: $username." }

        usuarios.values.forEach {
            if (it.username == username)
                return it
        }
        return null
    }

    /**
     * Función para hacer login de un usuario.
     */
    override suspend fun login(username: String, password: String): Usuario? {
        logger.info { "login($username, $password) - Login del usuario: $username"}

        val usuario = findByUserame(username)

        usuario?.let {
            if (Bcrypt.verify(password, usuario.password.encodeToByteArray())) {
                return usuario
            } else {
                return null
            }
        }
        return null
    }

    /**
     * Función para listar todos los usuarios.
     */
    override suspend fun findAll(): Flow<Usuario> {
        logger.debug { "findAll() - Buscando a todos los usuarios." }

        return usuarios.values.toList().asFlow()
    }

    /**
     * Función para encontrar un usuario en base a su UUID.
     *
     * @param id El UUID que lo identifica.
     */
    override suspend fun findByUUID(id: UUID): Usuario? = withContext(Dispatchers.IO) {
        logger.debug { "findById($id) - Buscando a un usuario con uuid: $id." }

        return@withContext usuarios[id]
    }

    /**
     * Función para guardar un usuario.
     *
     * @param entity Usuario a crear.
     */
    override suspend fun save(entity: Usuario): Usuario = withContext(Dispatchers.IO) {
        logger.debug { "save($entity) - Salvando un usuario." }

        usuarios[entity.uuid] = entity
        return@withContext entity
    }

    /**
     * Función para actualizar un usuario.
     *
     * @param id El identificador del usuario.
     * @param entity El nuevo usuario.
     */
    override suspend fun update(id: UUID, entity: Usuario): Usuario = withContext(Dispatchers.IO) {
        logger.debug { "update($id, $entity) - Actualizando al usuario con el uuid: $id.)" }

        val usuario = entity.copy(
            uuid = entity.uuid,
            username = entity.username,
            password = entity.password,
            role = entity.role
        )

        usuarios[id] = usuario
        return@withContext usuario
    }

    /**
     * Función para eliminar un usuario en base a su id.
     *
     * @param id El id del usuario.
     */
    override suspend fun delete(id: UUID): Usuario? = withContext(Dispatchers.IO) {
        logger.debug { "delete($id) - Borrando el usuario con el uuid: $id. " }

        return@withContext usuarios.remove(id)
    }
}