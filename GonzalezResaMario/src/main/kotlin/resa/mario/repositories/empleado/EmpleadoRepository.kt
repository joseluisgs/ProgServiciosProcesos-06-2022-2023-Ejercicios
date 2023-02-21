package resa.mario.repositories.empleado

import resa.mario.models.Empleado
import resa.mario.repositories.CrudRepository
import java.util.UUID

interface EmpleadoRepository : CrudRepository<Empleado, UUID> {
}