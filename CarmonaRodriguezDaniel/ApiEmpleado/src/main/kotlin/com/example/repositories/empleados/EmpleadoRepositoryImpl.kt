package com.example.repositories.empleados

import com.example.exceptions.EmpleadoBadRequestException
import com.example.exceptions.EmpleadoNotFoundException
import com.example.models.Empleado
import com.example.models.Notification
import com.example.services.FileCsvService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
class EmpleadoRepositoryImpl : EmpleadoRepository {
    private val fileService: FileCsvService get() = FileCsvService()
    private var empleados = mutableMapOf<Long, Empleado>()
    private val suscriptores = mutableMapOf<Int, suspend (Notification<Empleado>) -> Unit>()

    init {
        empleados = fileService.readFileEmpleado()
    }

    override suspend fun findAll(): Flow<Empleado> {
        return empleados.values.toList().asFlow()
    }

    override suspend fun findById(id: Long): Empleado? {
        return empleados[id]
    }

    override suspend fun save(empleado: Empleado): Empleado {
        val result = empleados[empleado.id]
        result?.let {
            throw EmpleadoBadRequestException("Ya existe el empleado: ${empleado.name}")
        } ?: empleados.put(empleado.id!!, empleado).also {
            fileService.writeEmpleado(empleado)
            onChange(Notification.Tipo.CREATE, empleado.id!!, empleado)
        }
        return empleado
    }

    override suspend fun delete(empleado: Empleado): Boolean {
        return empleados.remove(empleado.id).also {
            onChange(Notification.Tipo.DELETE, empleado.id!!, empleado)
            fileService.writeFileEmpleado(empleados)
        }.let { true }
    }

    override suspend fun update(id: Long, empleado: Empleado): Empleado {
        val result = empleados[id]
        result?.let {
            empleado.id = id
            empleado.uuid = it.uuid
            empleado.departamento = it.departamento
            empleados[id] = empleado
            onChange(Notification.Tipo.UPDATE, id, empleado)
            fileService.writeFileEmpleado(empleados)
            return empleado
        } ?: throw EmpleadoNotFoundException("No existe el empleado con id: $id")
    }

    override fun addSuscriptor(id: Int, suscriptor: suspend (Notification<Empleado>) -> Unit) {
        suscriptores[id] = suscriptor
    }

    override fun removeSuscriptor(id: Int) {
        suscriptores.remove(id)
    }

    private suspend fun onChange(tipo: Notification.Tipo, id: Long, data: Empleado? = null) {
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
            suscriptores.values.forEach {
                it.invoke(
                    Notification<Empleado>(
                        entity = "Empleado",
                        tipo = tipo,
                        data = data!!,
                        id = id
                    )
                )
            }
        }
    }
}