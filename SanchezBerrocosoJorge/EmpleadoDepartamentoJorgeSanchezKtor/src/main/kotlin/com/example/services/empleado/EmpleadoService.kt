package com.example.services.empleado

import com.example.models.Empleado
import com.example.repositories.empleados.EmpleadosRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single
import java.util.*

@Single
class EmpleadoService(
    private val empleadoRepository: EmpleadosRepository,
) : IEmpleadoService {
    override suspend fun findAll(): Flow<Empleado> {
        return empleadoRepository.findAll()

    }

    override suspend fun findById(id: UUID): Empleado {
        val user = empleadoRepository.findById(id)
        return user!!
    }

    override suspend fun save(empleado: Empleado): Empleado {
        empleadoRepository.save(empleado)
        return empleado
    }

    override suspend fun delete(empleado: Empleado): Empleado {
        empleadoRepository.delete(empleado)
        return empleado
    }

    override suspend fun update(empleado: Empleado): Empleado {
        empleadoRepository.update(empleado)
        return empleado
    }
}