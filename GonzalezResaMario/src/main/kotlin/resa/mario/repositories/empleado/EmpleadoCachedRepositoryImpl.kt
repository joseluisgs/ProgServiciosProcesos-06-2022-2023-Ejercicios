package resa.mario.repositories.empleado

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import resa.mario.models.Empleado
import resa.mario.services.cache.EmpleadoCache
import java.util.*

private val log = KotlinLogging.logger {}

/**
 * Repositorio de empleados cacheado que trabaja con la cache y el repositorio original
 *
 * @property repository
 * @property cache
 */
@Single
@Named("EmpleadoCachedRepository")
class EmpleadoCachedRepositoryImpl(
    @Named("EmpleadoRepository")
    private val repository: EmpleadoRepositoryImpl,
    private val cache: EmpleadoCache
) : EmpleadoRepository {

    private var refreshJob: Job? = null // Job para cancelar la ejecucion

    // Leyendo el enunciado, entiendo que en este caso si se actualizara cada x tiempo
    init {
        refreshCache()
    }

    private fun refreshCache() {
        if (refreshJob != null) refreshJob?.cancel()

        refreshJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                delay(cache.refreshTime.toLong())

                log.info { "Refrescando cache de empleados" }
                findAll().collect {
                    cache.cache.put(it.id, it)
                }

                // Ubicacion original del delay, aqui daba fallo en un caso muy especifico
                // Crear un empleado sin hacer ninguna operacion antes, eso si, el empleado se seguia creando.
            }
        }
    }

    override suspend fun findAll(): Flow<Empleado> {
        // Aunque este cacheado, existe un limite en la cache, y en este caso me interesan todos los datos, asi que
        // devolvere los datos del repositorio directamente
        log.info { "Buscando a todos los empleados" }

        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Empleado? {
        log.info { "Buscando empleado en cache con id: $id" }

        var existe = cache.cache.get(id)
        if (existe == null) {
            log.info { "Empleado no encontrado en cache" }

            existe = repository.findById(id)?.also { cache.cache.put(id, it) }
        }
        return existe
    }

    override suspend fun save(entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        log.info { "Almacenando empleado en cache y base de datos" }

        launch {
            cache.cache.put(entity.id, entity)
        }

        launch {
            repository.save(entity)
        }

        return@withContext entity
    }

    override suspend fun update(id: UUID, entity: Empleado): Empleado = withContext(Dispatchers.IO) {
        log.info { "Actualizando empleado en cache y base de datos" }

        launch {
            cache.cache.put(id, entity)
        }

        launch {
            repository.update(id, entity)
        }

        return@withContext entity
    }

    override suspend fun delete(entity: Empleado): Empleado? = withContext(Dispatchers.IO) {
        log.info { "Borrando empleado en cache y en base de datos: $entity" }

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