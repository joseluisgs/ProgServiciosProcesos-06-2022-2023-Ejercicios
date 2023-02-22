package com.example.repositories.empleados

import com.example.models.Empleado
import com.example.repositories.CrudRepository
import java.util.*

interface IEmpleadoRepository : CrudRepository<UUID, Empleado>{
}