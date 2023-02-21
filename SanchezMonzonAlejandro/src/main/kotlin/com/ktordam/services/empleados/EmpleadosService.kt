package com.ktordam.services.empleados

import com.ktordam.exceptions.EmpleadoNotFoundException
import com.ktordam.models.Empleado
import com.ktordam.repositories.empleados.EmpleadosRepository
import kotlinx.coroutines.flow.Flow
import mu.KotlinLogging
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

private val logger = KotlinLogging.logger {}

@Single
class EmpleadosService(
    @Named("EmpleadosRepository")
    private val empleadosRepository: EmpleadosRepository
): IEmpleadosService {
    override suspend fun findAll(): Flow<Empleado> {
        return empleadosRepository.findAll()
    }

    override suspend fun findById(id: Int): Empleado {
        return empleadosRepository.findByUUID(id)?: throw EmpleadoNotFoundException("No se ha encontrado el empleado con uuid: $id")
    }

    override suspend fun save(empleado: Empleado): Empleado {
        return empleadosRepository.save(empleado)
    }

    override suspend fun update(id: Int, empleado: Empleado): Empleado {
        val existe = empleadosRepository.findByUUID(id)

        existe?.let {
            return empleadosRepository.update(id, empleado)
        } ?: throw EmpleadoNotFoundException("No se ha encontrado el empleado con uuid: $id")
    }

    override suspend fun delete(id: Int): Empleado? {
        val existe = empleadosRepository.findByUUID(id)

        existe?.let {
            return empleadosRepository.delete(id)
        } ?: throw EmpleadoNotFoundException("No se ha encontrado el empleado con uuid: $id")
    }
}