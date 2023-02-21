package rodriguez.daniel.services.cache.departamento

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import rodriguez.daniel.model.Departamento
import java.util.UUID
import kotlin.time.Duration.Companion.minutes

@Single
class DepartamentoCache : IDepartamentoCache {
    override val cache = Cache.Builder()
        .maximumCacheSize(10)
        .expireAfterAccess(2.minutes)
        .build<UUID, Departamento>()

    init { println("Iniciando cache de departamentos.") }
}