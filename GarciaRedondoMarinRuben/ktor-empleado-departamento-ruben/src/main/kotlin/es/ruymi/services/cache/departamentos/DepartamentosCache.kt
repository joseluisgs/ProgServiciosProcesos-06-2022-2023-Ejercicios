package es.ruymi.services.cache.departamentos

import es.ruymi.models.Departamento
import es.ruymi.services.cache.ICache
import java.util.*

interface DepartamentosCache : ICache<UUID, Departamento>