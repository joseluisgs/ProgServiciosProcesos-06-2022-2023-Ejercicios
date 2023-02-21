package com.example.repositories

interface ICRUD<T, ID> {
    fun update(id: ID, item: T):T
    fun findAll():List<T>
    fun findById(id: ID):T?
    fun save(item: T):T
    fun delete(id: ID):Boolean
}