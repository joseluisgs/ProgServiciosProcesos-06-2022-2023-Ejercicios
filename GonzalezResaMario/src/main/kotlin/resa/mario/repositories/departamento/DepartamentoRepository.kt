package resa.mario.repositories.departamento

import resa.mario.models.Departamento
import resa.mario.repositories.CrudRepository
import java.util.UUID

interface DepartamentoRepository : CrudRepository<Departamento, UUID> {
}