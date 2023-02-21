package resa.mario.repositories.departamento

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Departamento
import resa.mario.services.cache.DepartamentoCache
import java.util.*

private val log = KotlinLogging.logger {}

/**
 * Repositorio de departamentos cacheado que trabaja con la cache y el repositorio original
 *
 * @property repository
 * @property cache
 */
@Single
@Named("DepartamentoCachedRepository")
class DepartamentoCachedRepositoryImpl(
    @Named("DepartamentoRepository")
    private val repository: DepartamentoRepositoryImpl,
    private val cache: DepartamentoCache
) : DepartamentoRepository {

    override suspend fun findAll(): Flow<Departamento> {
        // No voy a introducir todos los datos en la cache, asi que este metodo siempre usa el repositorio no cacheado
        // Por lo tanto, no habra actualizacion en segundo plano cada x segundos
        log.info { "Obteniendo a todos los departamentos // NO CACHEADO" }

        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Departamento? {
        log.info { "Buscando departamento en cache con id: $id" }

        var existe = cache.cache.get(id)
        if (existe == null) {
            log.info { "Departamento no encontrado en cache" }

            existe = repository.findById(id)?.also { cache.cache.put(id, it) }
        }
        return existe
    }

    override suspend fun save(entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        log.info { "Almacenando departamento en cache y base de datos" }

        launch {
            cache.cache.put(entity.id, entity)
        }

        launch {
            repository.save(entity)
        }

        return@withContext entity
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento = withContext(Dispatchers.IO) {
        log.info { "Actualizando departamento en cache y en base de datos" }


        launch {
            cache.cache.put(id, entity)
        }

        launch {
            repository.update(id, entity)
        }

        return@withContext entity
    }

    override suspend fun delete(entity: Departamento): Departamento? = withContext(Dispatchers.IO) {
        log.info { "Borrando departamento en cache y en base de datos: $entity" }

        val existe = findById(entity.id)

        return@withContext existe?.let {
            launch {
                cache.cache.invalidate(entity.id)
            }

            launch {
                repository.delete(entity)
            }
            return@let existe
        }
    }
}