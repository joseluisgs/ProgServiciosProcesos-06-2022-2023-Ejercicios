package com.example.services.departamento

import com.example.models.Departamento
import com.example.repositories.departamentos.DepartamentoRepositoryImpl
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import java.util.*

@Single
class DepartamentoService(
    private val departamentoRepository: DepartamentoRepositoryImpl,
    private val departamentoRepositoryCache: DepartamentoRepositoryImpl
) : IDepartamentoService {

    override suspend fun findAll(): Flow<Departamento> {
        return departamentoRepository.findAll()
    }

    override suspend fun findById(id: UUID): Departamento {
        val a =  departamentoRepository.findById(id)
        return a!!
    }

    override suspend fun save(departamento: Departamento): Departamento {
        departamentoRepository.save(departamento)
        departamentoRepositoryCache.save(departamento)
        return departamento
    }

    override suspend fun delete(departamento: Departamento): Departamento {
        departamentoRepository.delete(departamento)
        departamentoRepositoryCache.delete(departamento)
        return departamento
    }

    override suspend fun update(departamento: Departamento): Departamento {
        departamentoRepository.update(departamento)
        departamentoRepositoryCache.update(departamento)
        return departamento
    }

}