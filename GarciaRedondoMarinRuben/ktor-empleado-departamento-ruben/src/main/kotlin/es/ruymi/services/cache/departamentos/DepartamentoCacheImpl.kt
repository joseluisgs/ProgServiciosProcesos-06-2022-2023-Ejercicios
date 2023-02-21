package es.ruymi.services.cache.departamentos

import es.ruymi.models.Departamento
import io.github.reactivecircus.cache4k.Cache
import mu.KotlinLogging
import org.koin.core.annotation.Single
import java.util.*
import kotlin.time.Duration.Companion.minutes

private val logger = KotlinLogging.logger {}

@Single
class DepartamentoCacheImpl : DepartamentosCache {
    override val hasRefreshAllCacheJob: Boolean = false
    override val refreshTime = 30 * 60 * 1000L

    override val cache = Cache.Builder()
        .maximumCacheSize(100)
        .expireAfterAccess(30.minutes)
        .build<UUID, Departamento>()

    init {
        logger.debug { "Iniciando el sistema de caché de departamentos" }
    }
}