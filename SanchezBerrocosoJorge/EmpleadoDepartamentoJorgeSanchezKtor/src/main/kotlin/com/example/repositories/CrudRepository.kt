package com.example.repositories

import kotlinx.coroutines.flow.Flow

interface CrudRepository<ID,T> {
    suspend fun findAll(): Flow<T>
    suspend fun findById(id: ID): T?
    suspend fun delete(item: T): T?
    suspend fun save(item: T): T
    suspend fun update(item: T): T
}