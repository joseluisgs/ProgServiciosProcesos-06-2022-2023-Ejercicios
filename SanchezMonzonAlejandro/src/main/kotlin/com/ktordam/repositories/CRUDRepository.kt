package com.ktordam.repositories

import kotlinx.coroutines.flow.Flow

interface CRUDRepository<T, ID> {
    suspend fun findAll(): Flow<T>
    suspend fun findByUUID(id: ID): T?
    suspend fun save(entity: T): T
    suspend fun update(id: ID, entity: T): T
    suspend fun delete(id: ID): T?
}