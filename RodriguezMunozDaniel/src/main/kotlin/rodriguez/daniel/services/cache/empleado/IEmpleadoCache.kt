package rodriguez.daniel.services.cache.empleado

import rodriguez.daniel.model.Empleado
import rodriguez.daniel.services.cache.ICache
import java.util.UUID

interface IEmpleadoCache: ICache<UUID, Empleado>