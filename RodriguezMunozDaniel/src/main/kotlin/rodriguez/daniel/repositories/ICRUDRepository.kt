package rodriguez.daniel.repositories

import kotlinx.coroutines.flow.Flow

interface ICRUDRepository<T, ID> {
    suspend fun findAll(): Flow<T>
    suspend fun findById(id: ID): T?
    suspend fun save(entity: T): T
    suspend fun delete(id: ID): T?
}