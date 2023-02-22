package drodriguez.es.repositories.departamento

import drodriguez.es.models.Departamento
import drodriguez.es.repositories.CrudRepository
import java.util.*

interface IDepartamentoRepository: CrudRepository<Departamento, UUID> {
}