package com.example.repositories.empleados

import com.example.models.Empleado
import com.example.repositories.ICRUD
import org.koin.core.annotation.Single
import java.util.*


interface IEmpleadoRepository : ICRUD<Empleado, UUID> {
    suspend fun findByEmail(email : String) : Empleado?
}