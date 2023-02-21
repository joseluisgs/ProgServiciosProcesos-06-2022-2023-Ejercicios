package com.ktordam.services.cache

import com.ktordam.models.Empleado
import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
class EmpleadosCache {
    val cache = Cache.Builder()
        .maximumCacheSize(10)
        .expireAfterAccess(10.minutes)
        .build<Int, Empleado>()
}