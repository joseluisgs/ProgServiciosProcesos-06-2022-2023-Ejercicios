package mendoza.js.repositories.empleado

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import mendoza.js.models.Empleado
import mendoza.js.service.cache.empleado.EmpleadoCache
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger { }

@Single
@Named("EmpleadoCachedRepository")
class EmpleadoCachedRepository(
    @Named("EmpleadoRepository")
    private val repository: EmpleadoRepository,
    private val cache: EmpleadoCache
) : EmpleadoRepository {

    private var refreshJob: Job? = null

    init {
        logger.debug { "Iniciando el repositorio cache empleado. AutoRefreshAll: ${cache.hasRefreshAllCacheJob}" }
        if (cache.hasRefreshAllCacheJob)
            refreshCacheJob()
    }

    private fun refreshCacheJob() {
        if (refreshJob != null)
            refreshJob?.cancel()

        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            do {
                logger.debug { "refreshCache: Refrescando cache de empleado" }
                repository.findAll().collect { empleado ->
                    cache.cache.put(empleado.id, empleado)
                }
                logger.debug { "refreshCache: Cache actualizada: ${cache.cache.asMap().values.size}" }
                delay(cache.refreshTime)
            } while (true)
        }
    }

    override suspend fun findByNombre(nombre: String): Flow<Empleado> {
        logger.debug { "findByNombre: Buscando empleado con nombre: $nombre" }
        return repository.findByNombre(nombre)
    }

    override suspend fun findAll(): Flow<Empleado> {
        logger.debug { "findAll: Buscando todos los empleados en cache" }
        return if (!cache.hasRefreshAllCacheJob || cache.cache.asMap().isEmpty()) {
            logger.debug { "findAll: Devolviendo datos de repositorio" }
            repository.findAll()
        } else {
            logger.debug { "findAll. Devolviendo datos de cache" }
            cache.cache.asMap().values.asFlow()
        }
    }

    override suspend fun findById(id: UUID): Empleado? {
        logger.debug { "findById: Buscando empleado en cache con id $id" }
        return cache.cache.get(id) ?: repository.findById(id)?.also { cache.cache.put(id, it) }
    }

    override suspend fun save(entity: Empleado): Empleado {
        logger.debug { "save: Guardando empleado en cache" }
        val emp = entity.copy(id = UUID.randomUUID(), createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now())
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            cache.cache.put(emp.id, emp)
        }
        scope.launch {
            repository.save(entity)
        }
        return emp
    }

    override suspend fun update(id: UUID, entity: Empleado): Empleado? {
        logger.debug { "update: Actualizando empleado en cache" }
        val existe = findById(id)
        return existe?.let {
            val emp = entity.copy(id = id, createdAt = existe!!.createdAt, updatedAt = LocalDateTime.now())
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                cache.cache.put(emp.id, emp)
            }
            scope.launch {
                repository.update(id, emp)
            }
            return emp
        }
    }

    override suspend fun delete(entity: Empleado): Empleado? {
        logger.debug { "delete: Eliminando empleado en cache" }
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