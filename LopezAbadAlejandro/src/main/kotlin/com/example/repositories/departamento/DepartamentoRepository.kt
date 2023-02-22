package com.example.repositories.departamento

import com.example.models.Departamento
import com.example.repositories.CrudRepository
import java.util.*

interface DepartamentoRepository:CrudRepository<Departamento, UUID> {
}