package rodriguez.daniel.services.cache

import io.github.reactivecircus.cache4k.Cache

interface ICache<ID : Any, T : Any> {
    val cache: Cache<ID, T>
}