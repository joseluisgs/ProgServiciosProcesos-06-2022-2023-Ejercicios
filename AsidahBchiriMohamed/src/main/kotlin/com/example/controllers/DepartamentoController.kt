package com.example.controllers

import com.example.dtos.DepartamentoPatchDto
import com.example.exceptions.DepartamentoBadRequest
import com.example.exceptions.DepartamentoNotFound
import com.example.exceptions.EmpleadoNotFound
import com.example.models.Departamento
import com.example.models.Empleado
import com.example.repositories.departamentos.IDepartamentoRepository
import com.example.repositories.empleados.EmpleadoRepository
import com.example.repositories.empleados.IEmpleadoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.count
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class DepartamentoController(private val repository: IDepartamentoRepository) {
    suspend fun findById(id: String): Departamento {
        return repository.findById(UUID.fromString(id)) ?: throw DepartamentoNotFound("Departamento no encontrado")
    }

    suspend fun findAll(): Flow<Departamento> {
        try {
            val departamentos = repository.findAll()
            if (departamentos.count() > 0)
                return departamentos
            else
                throw DepartamentoNotFound("Departamento no encontrado")
        } catch (e: Exception) {
            throw DepartamentoBadRequest("Departamento no encontrado")
        }

    }

    suspend fun save(item: Departamento): Departamento {
        return repository.save(item) ?: throw DepartamentoBadRequest("Departamento no creado")
    }

    suspend fun delete(id: UUID): Boolean {
        val departamento = repository.findById(id)
        departamento?.let {
            return repository.delete(it)
        } ?: throw DepartamentoNotFound("Departamento no encontrado")
    }

    suspend fun addEmpleado(dto: DepartamentoPatchDto): Departamento {
        try {
            val departamento = repository.findById(UUID.fromString(dto.idDepartamento))
                ?: throw DepartamentoNotFound("Departamento no encontrado")
            val empleado = EmpleadoRepository().findById(UUID.fromString(dto.idEmpleado)) ?: throw EmpleadoNotFound(
                "Empleado no encontrado"
            )
            departamento.empleados.add(empleado)
            return repository.update(departamento) ?: throw DepartamentoBadRequest("Departamento no actualizado")
        } catch (e: Exception) {
            throw DepartamentoBadRequest("Departamento no actualizado")
        }
    }

}
