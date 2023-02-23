package com.example.repositories

import kotlinx.coroutines.flow.Flow

interface ICRUD<T,ID> {
    suspend fun findAll() : Flow<T>
    suspend fun findById(id : ID) : T?
    suspend fun save(item : T) : T?
    suspend fun update(item : T) : T?
    suspend fun delete(item: T) : Boolean
    suspend fun deleteAll()
}