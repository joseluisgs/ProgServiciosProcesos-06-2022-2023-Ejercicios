package com.ktordam.services.cache

import com.ktordam.models.Departamento
import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.minutes

@Single
class DepartamentosCache {
    val cache = Cache.Builder()
        .maximumCacheSize(10)
        .expireAfterAccess(10.minutes)
        .build<Int, Departamento>()
}