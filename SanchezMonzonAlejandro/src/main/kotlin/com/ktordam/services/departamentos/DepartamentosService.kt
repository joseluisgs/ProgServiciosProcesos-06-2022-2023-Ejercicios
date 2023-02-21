package com.ktordam.services.departamentos

import com.ktordam.exceptions.EmpleadoNotFoundException
import com.ktordam.models.Departamento
import com.ktordam.repositories.departamentos.DepartamentosRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Single
class DepartamentosService(
    @Named("DepartamentosRepository")
    private val departamentosRepository: DepartamentosRepository
): IDepartamentosService {
    override suspend fun findAll(): Flow<Departamento> {
        return departamentosRepository.findAll()
    }

    override suspend fun findById(id: Int): Departamento {
        return departamentosRepository.findByUUID(id)?: throw EmpleadoNotFoundException("No se ha encontrado el departamento con uuid: $id")
    }

    override suspend fun save(departamento: Departamento): Departamento {
        return departamentosRepository.save(departamento)
    }

    override suspend fun update(id: Int, departamento: Departamento): Departamento {
        val existe = departamentosRepository.findByUUID(id)

        existe?.let {
            return departamentosRepository.update(id, departamento)
        } ?: throw EmpleadoNotFoundException("No se ha encontrado el departamento con uuid: $id")
    }

    override suspend fun delete(id: Int): Departamento? {
        val existe = departamentosRepository.findByUUID(id)

        existe?.let {
            return departamentosRepository.delete(id)
        } ?: throw EmpleadoNotFoundException("No se ha encontrado el departamento con uuid: $id")
    }

}