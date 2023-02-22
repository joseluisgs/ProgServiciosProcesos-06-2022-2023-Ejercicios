package com.example.services.departamento

import com.example.models.Departamento
import kotlinx.coroutines.flow.Flow
import java.util.*

interface IDepartamentoService {
    suspend fun findAll(): Flow<Departamento>
    suspend fun findById(id: UUID): Departamento
    suspend fun save(departamento: Departamento): Departamento
    suspend fun delete(departamento: Departamento): Departamento
    suspend fun update(departamento: Departamento): Departamento
}