package resa.mario.services.cache

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import resa.mario.models.Empleado
import java.util.*
import kotlin.time.Duration.Companion.minutes

/**
 * Servicio de cache para empleados
 *
 */
@Single
class EmpleadoCache {

    val refreshTime = 1000 * 60 // 1 minuto

    val cache = Cache.Builder()
        .maximumCacheSize(10)
        .expireAfterAccess(5.minutes)
        .build<UUID, Empleado>()
}