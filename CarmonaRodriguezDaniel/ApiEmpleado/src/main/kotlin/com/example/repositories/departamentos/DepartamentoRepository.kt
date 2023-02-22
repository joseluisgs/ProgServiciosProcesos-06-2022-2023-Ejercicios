package com.example.repositories.departamentos

import com.example.models.Departamento
import com.example.models.Notification
import kotlinx.coroutines.flow.Flow

interface DepartamentoRepository {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: Long): Departamento?
    suspend fun findByName(name: String): Departamento?
    suspend fun save(departamento: Departamento): Departamento
    suspend fun delete(departamento: Departamento): Boolean
    suspend fun update(id: Long, departamento: Departamento): Departamento

    fun addSuscriptor(id: Int, suscriptor: suspend (Notification<Departamento>) -> Unit)
    fun removeSuscriptor(id: Int)

}