package com.example.repositories.empleado

import com.example.models.Empleado
import com.example.repositories.CrudRepository
import java.util.*

interface EmpleadoRepository:CrudRepository<Empleado, UUID> {
}