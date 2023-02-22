package com.example.services.cache

import com.example.models.Departamento
import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import java.util.UUID
import kotlin.time.Duration.Companion.minutes

@Single
class DepartamentoCache {

    val cache = Cache.Builder().maximumCacheSize(10).expireAfterAccess(6.minutes).build<UUID,Departamento>()
}