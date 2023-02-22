package drodriguez.es.repositories.empleado

import drodriguez.es.models.Empleado
import drodriguez.es.repositories.CrudRepository
import java.util.UUID

interface IEmpleadoRepository: CrudRepository<Empleado, UUID> {
}