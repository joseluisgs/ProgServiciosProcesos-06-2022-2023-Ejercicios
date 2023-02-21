package rodriguez.daniel.repositories.empleado

import rodriguez.daniel.model.Empleado
import rodriguez.daniel.repositories.ICRUDRepository
import java.util.UUID

interface IEmpleadoRepository : ICRUDRepository<Empleado, UUID>