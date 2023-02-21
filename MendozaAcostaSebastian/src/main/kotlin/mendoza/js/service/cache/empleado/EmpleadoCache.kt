package mendoza.js.service.cache.empleado

import mendoza.js.models.Empleado
import mendoza.js.service.cache.ICache
import java.util.UUID

interface EmpleadoCache : ICache<UUID, Empleado>