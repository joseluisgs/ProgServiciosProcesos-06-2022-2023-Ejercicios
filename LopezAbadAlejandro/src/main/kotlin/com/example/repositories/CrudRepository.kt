package com.example.repositories

interface CrudRepository<T,ID> {
    fun findAll():List<T>
    fun findById(id: ID):T?
    fun update(id: ID, item: T):T
    fun save(item: T):T
    fun delete(id: ID):Boolean
}