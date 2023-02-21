package mendoza.js.service.cache.departamento

import mendoza.js.models.Departamento
import mendoza.js.service.cache.ICache
import java.util.UUID

interface DepartamentoCache : ICache<UUID, Departamento>