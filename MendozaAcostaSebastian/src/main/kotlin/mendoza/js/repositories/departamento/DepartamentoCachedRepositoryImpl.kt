package mendoza.js.repositories.departamento

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mendoza.js.models.Departamento
import mendoza.js.service.cache.departamento.DepartamentoCache
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger { }

@Single
@Named("DepartamentoCachedRepository")
class DepartamentoCachedRepositoryImpl(
    @Named("DepartamentoRepository")
    private val repository: DepartamentoRepository,
    private val cache: DepartamentoCache
) : DepartamentoRepository {

    private var refreshJob: Job? = null

    init {
        logger.debug { "Iniciando el repositorio cache departamento. AutoRefreshAll: ${cache.hasRefreshAllCacheJob}" }
        if (cache.hasRefreshAllCacheJob)
            refreshCacheJob()
    }

    private fun refreshCacheJob() {
        if (refreshJob != null)
            refreshJob?.cancel()

        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            do {
                logger.debug { "refreshCache: Refrescando cache de departamento" }
                repository.findAll().collect { departamento ->
                    cache.cache.put(departamento.id, departamento)
                }
                logger.debug { "refreshCache: Cache actualizada: ${cache.cache.asMap().values.size}" }
                delay(cache.refreshTime)
            } while (true)
        }
    }

    override suspend fun findByNombre(nombre: String): Flow<Departamento> {
        logger.debug { "findByNombre: Buscando departamento con nombre: $nombre" }
        return repository.findByNombre(nombre)
    }

    override suspend fun findAll(): Flow<Departamento> {
        logger.debug { "findAll: Buscando todos los departamentos en cache" }
        return if (!cache.hasRefreshAllCacheJob || cache.cache.asMap().isEmpty()) {
            logger.debug { "findAll: Devolviendo datos de repositorio" }
            repository.findAll()
        } else {
            logger.debug { "findAll: Devolviendo datos de cache" }
            cache.cache.asMap().values.asFlow()
        }
    }

    override suspend fun findById(id: UUID): Departamento? {
        logger.debug { "findById: Buscando departamento en cache con id $id" }
        return cache.cache.get(id) ?: repository.findById(id)
            ?.also { cache.cache.put(id, it) }
    }

    override suspend fun save(entity: Departamento): Departamento {
        logger.debug { "save: Guardando departamento en cache" }
        val dpt = entity.copy(id = UUID.randomUUID(), createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now())
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            cache.cache.put(dpt.id, dpt)
        }
        scope.launch {
            repository.save(entity)
        }
        return dpt
    }

    override suspend fun update(id: UUID, entity: Departamento): Departamento? {
        logger.debug { "update: Actualizando departamento en cache" }
        val existe = findById(id)
        return entity?.let {
            val dpt = entity.copy(id = id, createdAt = existe!!.createdAt, updatedAt = LocalDateTime.now())
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                cache.cache.put(dpt.id, dpt)
            }
            scope.launch {
                repository.update(id, dpt)
            }
            return dpt
        }
    }

    override suspend fun delete(entity: Departamento): Departamento? {
        logger.debug { "delete: Eliminando departamento en cache" }
        val existe = findById(entity.id)
        return entity?.let {
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                cache.cache.invalidate(entity.id)
            }
            scope.launch {
                repository.delete(entity)
            }
            return existe
        }
    }

}