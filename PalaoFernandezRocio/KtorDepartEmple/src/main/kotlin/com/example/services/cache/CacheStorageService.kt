package com.example.services.cache

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import java.io.File
import java.util.*
import kotlin.time.Duration.Companion.minutes

@Single
class CacheStorageService {
    val cache = Cache.Builder().expireAfterAccess(5.minutes)
        .build<UUID, File>()
}