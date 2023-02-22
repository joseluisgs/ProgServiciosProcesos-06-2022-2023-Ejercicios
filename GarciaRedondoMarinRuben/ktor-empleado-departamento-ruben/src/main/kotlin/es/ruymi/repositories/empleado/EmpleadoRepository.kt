package es.ruymi.repositories.empleado

import es.ruymi.models.Empleado
import es.ruymi.repositories.CrudRepository
import java.util.*

interface EmpleadoRepository: CrudRepository<Empleado, UUID> {
}