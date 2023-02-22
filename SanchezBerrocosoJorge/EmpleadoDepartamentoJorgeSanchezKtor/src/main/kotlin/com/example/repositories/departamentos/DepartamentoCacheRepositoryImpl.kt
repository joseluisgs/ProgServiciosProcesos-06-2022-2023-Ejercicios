package com.example.repositories.departamentos

import com.example.models.Departamento
import com.example.services.cache.DepartamentoCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import java.util.*

@Single
@Named("DepartamentoCacheRepositoryImpl")
class DepartamentoCacheRepositoryImpl(
    @Named("DepartamentoRepositoryImpl")
    private val repository: IDepartamentosRepository,
    private val cache: DepartamentoCache
) : IDepartamentosRepository {



    override suspend fun findAll(): Flow<Departamento> {
        return repository.findAll()
    }

    override suspend fun findById(id: UUID): Departamento? {
        var depart = cache.cache.get(id)
        if (depart == null) {
            depart = repository.findById(id)
            cache.cache.put(id, depart!!)
        }
        return depart
    }

    override suspend fun delete(item: Departamento): Departamento? = withContext(Dispatchers.IO) {
        val depart = repository.findById(item.id)
        if (depart!= null) {
            cache.cache.invalidate(depart.id)
            repository.delete(depart)
        }
        return@withContext depart
    }

    override suspend fun save(item: Departamento): Departamento = withContext(Dispatchers.IO){
        cache.cache.put(item.id, item)
        repository.save(item)
        return@withContext item
    }

    override suspend fun update(item: Departamento): Departamento = withContext(Dispatchers.IO) {
        cache.cache.put(item.id, item)
        repository.update(item)
        return@withContext item
    }
}