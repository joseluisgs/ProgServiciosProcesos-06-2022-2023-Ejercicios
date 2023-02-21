package mendoza.js.service.cache.empleado

import io.github.reactivecircus.cache4k.Cache
import mendoza.js.models.Empleado
import mu.KotlinLogging
import org.koin.core.annotation.Single
import java.util.*
import kotlin.time.Duration.Companion.minutes

private val logger = KotlinLogging.logger { }

@Single
class EmpleadoCacheImpl : EmpleadoCache {
    override val hasRefreshAllCacheJob: Boolean = false
    override val refreshTime = 60 * 60 * 1000L
    override val cache = Cache.Builder()
        .maximumCacheSize(100)
        .expireAfterAccess(60.minutes)
        .build<UUID, Empleado>()

    init {
        logger.debug { "Iniciando el sistema de caché de empleados" }
    }
}