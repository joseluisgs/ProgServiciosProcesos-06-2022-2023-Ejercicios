package resa.mario.services.cache

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import resa.mario.models.Departamento
import java.util.UUID
import kotlin.time.Duration.Companion.minutes

/**
 * Servicio de cache para los departamentos
 *
 */
@Single
class DepartamentoCache {

    val cache = Cache.Builder()
        .maximumCacheSize(10)
        .expireAfterAccess(5.minutes)
        .build<UUID, Departamento>()
}