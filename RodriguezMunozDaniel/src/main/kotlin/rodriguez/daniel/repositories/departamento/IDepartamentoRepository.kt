package rodriguez.daniel.repositories.departamento

import rodriguez.daniel.model.Departamento
import rodriguez.daniel.repositories.ICRUDRepository
import java.util.UUID

interface IDepartamentoRepository : ICRUDRepository<Departamento, UUID>