package com.ktordam.services.departamentos

import com.ktordam.models.Departamento
import kotlinx.coroutines.flow.Flow

interface IDepartamentosService {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: Int): Departamento
    suspend fun save(departamento: Departamento): Departamento
    suspend fun update(id: Int, departamento: Departamento): Departamento
    suspend fun delete(id: Int): Departamento?
}