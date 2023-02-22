package com.example.repositories.departamentos

import com.example.exceptions.DepartamentoBadRequestException
import com.example.exceptions.EmpleadoNotFoundException
import com.example.models.Departamento
import com.example.models.Notification
import com.example.services.FileCsvService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
class DepartamentoRepositoryImpl : DepartamentoRepository {
    private val fileService: FileCsvService get() = FileCsvService()
    private var departamentos = mutableMapOf<Long, Departamento>()
    private val suscriptores = mutableMapOf<Int, suspend (Notification<Departamento>) -> Unit>()

    init {
        departamentos = fileService.readFileDepartamento()
    }

    override suspend fun findAll(): Flow<Departamento> {
        return departamentos.values.toList().asFlow()
    }

    override suspend fun findById(id: Long): Departamento? {
        return departamentos[id]
    }

    override suspend fun findByName(name: String): Departamento? {
        return departamentos.values.filter { it.name.equals(name) }.firstOrNull()
    }

    override suspend fun save(departamento: Departamento): Departamento {
        val result = departamentos[departamento.id]
        result?.let {
            throw DepartamentoBadRequestException("Ya existe el departamento: ${departamento.name}")
        } ?: departamentos.put(departamento.id!!, departamento).also {
            fileService.writeDepartamento(departamento)
            onChange(Notification.Tipo.CREATE, departamento.id!!, departamento)
        }
        return departamento
    }

    override suspend fun delete(departamento: Departamento): Boolean {
        return departamentos.remove(departamento.id).also {
            onChange(Notification.Tipo.DELETE, departamento.id!!, departamento)
            fileService.writeFileDepartamento(departamentos)
        }.let { true }
    }

    override suspend fun update(id: Long, departamento: Departamento): Departamento {
        val result = departamentos[id]
        result?.let {
            departamento.id = id
            departamento.uuid = it.uuid
            departamentos[id] = departamento
            onChange(Notification.Tipo.UPDATE, id, departamento)
            fileService.writeFileDepartamento(departamentos)
            return departamento
        } ?: throw EmpleadoNotFoundException("No existe el departamento con id: $id")
    }

    override fun addSuscriptor(id: Int, suscriptor: suspend (Notification<Departamento>) -> Unit) {
        suscriptores[id] = suscriptor
    }

    override fun removeSuscriptor(id: Int) {
        suscriptores.remove(id)
    }

    private suspend fun onChange(tipo: Notification.Tipo, id: Long, data: Departamento? = null) {
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
            suscriptores.values.forEach {
                it.invoke(
                    Notification<Departamento>(
                        entity = "Departamento",
                        tipo = tipo,
                        data = data!!,
                        id = id
                    )
                )
            }
        }
    }
}