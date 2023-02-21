package com.example.repositories.empleado

import com.example.models.Empleado
import com.example.repositories.ICRUD
import java.util.*

interface EmpleadoRepository: ICRUD<Empleado, UUID> {
    fun getEmpleadosByIdDepartamento(id: UUID): List<Empleado>
    fun findEmpleadoByEmail(email: String): Empleado?
}