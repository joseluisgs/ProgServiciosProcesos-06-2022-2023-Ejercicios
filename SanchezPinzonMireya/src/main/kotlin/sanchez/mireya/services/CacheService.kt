package sanchez.mireya.services

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import sanchez.mireya.models.Departamento
import kotlin.time.Duration.Companion.minutes

@Single
class CacheService {
    val cache = Cache.Builder().expireAfterAccess(2.minutes)
        .build<Int, Departamento>()
}