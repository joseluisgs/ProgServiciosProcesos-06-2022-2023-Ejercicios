package com.example.repositories.departamentos

import com.example.models.Departamento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("DepartamentoRepositoryImpl")
class DepartamentoRepositoryImpl : IDepartamentosRepository{

    val list = mutableListOf<Departamento>()


    override suspend fun findAll(): Flow<Departamento> {
        return list.asFlow()
    }

    override suspend fun findById(id: UUID): Departamento? {
        val depart = list.find { it.id == id }
        return depart
    }

    override suspend fun delete(item: Departamento): Departamento? {
        list.remove(item)
        return item
    }

    override suspend fun save(item: Departamento): Departamento {
        list.add(item)
        return item
    }

    override suspend fun update(item: Departamento): Departamento {
        val i = list.find { it.id == item.id }
        list.remove(i)
        list.add(item)
        return item
    }
}