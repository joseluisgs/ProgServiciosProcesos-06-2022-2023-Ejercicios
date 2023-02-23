package com.example.controllers

import com.example.dtos.EmpleadoPatchDto
import com.example.exceptions.EmpleadoBadRequest
import com.example.exceptions.EmpleadoNotFound
import com.example.models.Empleado
import com.example.repositories.empleados.EmpleadoRepository
import com.example.repositories.empleados.IEmpleadoRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import java.util.UUID


@Single
class EmpleadoController(private val repository: IEmpleadoRepository) {

    suspend fun findAll(): Flow<Empleado> {
        return repository.findAll()
    }

    suspend fun findById(id: String): Empleado {
        val empleado = repository.findById(UUID.fromString(id))
        return empleado ?: throw EmpleadoNotFound("Empleado no encontrado")
    }

    suspend fun findByEmail(email: String): Empleado? {
        val empleado = repository.findByEmail(email)
    return empleado ?: throw EmpleadoNotFound("Empleado no encontrado")
    }

    suspend fun save(item: Empleado): Empleado? {
        try {
            if (repository.findById(item.uuid) != null)
                return repository.update(item)
            else
                return repository.save(item)
        }catch (e : Exception){
            throw EmpleadoBadRequest("Empleado no creado")
        }
    }

    suspend fun delete(id: UUID): Boolean {
        val delete = repository.findById(id)
        delete?.let {
            return repository.delete(delete)
        }
        throw EmpleadoNotFound("No existe")
    }

    suspend fun patchEmpleado(empleado : Empleado, data: EmpleadoPatchDto): Empleado? {
        try{
            data.email?.let {
                empleado.email = it
            }
            data.departamento?.let {
                empleado.departamento = it
            }
            data.salary?.let {
                empleado.salary = it
            }
            return repository.update(empleado)

        }catch (e : Exception){
            throw EmpleadoBadRequest("Empleado no actualizado")
        }

    }
}