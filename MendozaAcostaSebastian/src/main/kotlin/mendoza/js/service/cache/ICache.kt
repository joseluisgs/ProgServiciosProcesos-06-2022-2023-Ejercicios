package mendoza.js.service.cache

import io.github.reactivecircus.cache4k.Cache

interface ICache<ID : Any, T : Any> {
    val hasRefreshAllCacheJob: Boolean
    val refreshTime: Long
    val cache: Cache<ID, T>
}