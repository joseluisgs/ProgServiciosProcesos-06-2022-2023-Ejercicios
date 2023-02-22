package com.example.services.cache

import io.github.reactivecircus.cache4k.Cache
import org.koin.core.annotation.Single
import java.io.File
import java.util.*
import kotlin.time.Duration.Companion.minutes

@Single
class CacheService {
    val cach= Cache.Builder().expireAfterAccess(3.minutes).build<UUID, File>()
}