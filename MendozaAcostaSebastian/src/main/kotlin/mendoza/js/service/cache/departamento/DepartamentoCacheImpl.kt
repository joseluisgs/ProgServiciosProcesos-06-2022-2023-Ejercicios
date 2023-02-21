package mendoza.js.service.cache.departamento

import io.github.reactivecircus.cache4k.Cache
import mendoza.js.models.Departamento
import mu.KotlinLogging
import org.koin.core.annotation.Single
import java.util.*
import kotlin.time.Duration.Companion.minutes

private val logger = KotlinLogging.logger { }

@Single
class DepartamentoCacheImpl : DepartamentoCache {
    override val hasRefreshAllCacheJob: Boolean = false
    override val refreshTime = 60 * 60 * 1000L
    override val cache = Cache.Builder()
        .maximumCacheSize(100)
        .expireAfterAccess(60.minutes)
        .build<UUID, Departamento>()

    init {
        logger.debug { "Iniciando el sistema de caché de departamentos" }
    }
}