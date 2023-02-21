package sanchez.mireya.repositories.empleado

import sanchez.mireya.models.Empleado
import sanchez.mireya.repositories.CRUDRepository

interface IEmpleadosRepository: CRUDRepository<Empleado, Int> {
}