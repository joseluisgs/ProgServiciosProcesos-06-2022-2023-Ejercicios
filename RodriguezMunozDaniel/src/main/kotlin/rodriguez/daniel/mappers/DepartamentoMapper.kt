package rodriguez.daniel.mappers

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import rodriguez.daniel.dto.DepartamentoDTO
import rodriguez.daniel.dto.DepartamentoDTOcreacion
import rodriguez.daniel.model.Departamento
import rodriguez.daniel.repositories.empleado.EmpleadoRepository

private val eRepo = EmpleadoRepository()

suspend fun Departamento.toDTO() = DepartamentoDTO(
    nombre, presupuesto,
    eRepo.findAll().filter { it.departamentoId == id }.map { it.toDTO() }.toList()
)
fun DepartamentoDTOcreacion.fromDTO() =
    Departamento(id = id, nombre = nombre, presupuesto = presupuesto)