package com.example.repositories.departamentos

import com.example.models.Departamento
import com.example.repositories.CrudRepository
import java.util.*

interface IDepartamentosRepository : CrudRepository<UUID, Departamento> {
}