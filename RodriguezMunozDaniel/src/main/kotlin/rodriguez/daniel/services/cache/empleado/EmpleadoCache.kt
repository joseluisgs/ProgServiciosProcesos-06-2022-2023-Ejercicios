package rodriguez.daniel.services.cache.empleado

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import rodriguez.daniel.model.Empleado
import java.util.UUID
import kotlin.time.Duration.Companion.minutes

@Single
class EmpleadoCache : IEmpleadoCache {
    override val cache = Cache.Builder()
        .maximumCacheSize(10)
        .expireAfterAccess(2.minutes)
        .build<UUID, Empleado>()

    init { println("Iniciando cache de empleados.") }
}