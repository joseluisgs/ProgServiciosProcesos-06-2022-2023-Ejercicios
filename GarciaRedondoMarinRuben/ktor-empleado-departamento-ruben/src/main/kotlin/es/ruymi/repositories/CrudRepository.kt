package es.ruymi.repositories

import es.ruymi.models.User
import kotlinx.coroutines.flow.Flow

interface CrudRepository<T, ID> {
    suspend fun findAll(): Flow<T>
    suspend fun findById(id: ID): T?
    suspend fun insert(entity: T): T
    suspend fun update(entity: T): T?
    suspend fun delete(entity: T): Boolean

}