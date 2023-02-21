package sanchez.mireya.repositories.usuario

import com.toxicbakery.bcrypt.Bcrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import sanchez.mireya.db.getUsuarios
import sanchez.mireya.models.Usuario
import sanchez.mireya.repositories.departamento.logger
import java.util.*

val logger = KotlinLogging.logger {}

@Single
@Named("UsuariosRepository")
class UsuariosRepository : IUsuariosRepository {

    private val usuarios: MutableMap<UUID, Usuario> = mutableMapOf()

    init {
        logger.info { "Iniciando repositorio usuarios" }

        getUsuarios().forEach {
            usuarios[it.id] = it
        }
    }

    override suspend fun findAll(): Flow<Usuario> {
        logger.info { "findAll()" }

        return usuarios.values.toList().asFlow()
    }

    override suspend fun findById(id: UUID): Usuario? {
        logger.info { "findById(): $id" }

        return usuarios[id]
    }

    private fun findByUsername(username: String): Usuario? {
        logger.info { "findByUsername(): $username" }

        return usuarios.filter { it.value.username == username }.map { it.value }[0]

    }

    override suspend fun save(entity: Usuario): Usuario {
        logger.info { "save(): $entity" }

        usuarios[entity.id] = entity
        return entity
    }

    override suspend fun update(id: UUID, entity: Usuario): Usuario? {
        logger.info { "update(): ${entity.id}" }

        usuarios[id] = entity

        return entity
    }

    override suspend fun delete(id: UUID): Usuario? {
        logger.info { "delete(): $id" }

        return usuarios.remove(id)
    }

    fun login(username: String, password: String): Usuario? {
        logger.info { "login(): $username" }

        val existe = findByUsername(username) ?: return null

        existe.let {
            if (Bcrypt.verify(password, existe.password.encodeToByteArray())) {
                return existe
            } else return null
        }
    }


}