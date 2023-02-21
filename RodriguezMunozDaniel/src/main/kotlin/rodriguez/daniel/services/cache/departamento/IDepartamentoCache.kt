package rodriguez.daniel.services.cache.departamento

import rodriguez.daniel.model.Departamento
import rodriguez.daniel.services.cache.ICache
import java.util.UUID

interface IDepartamentoCache: ICache<UUID, Departamento>