package rodriguez.daniel.services.departamento

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import rodriguez.daniel.db.departamentos
import rodriguez.daniel.dto.*
import rodriguez.daniel.exception.DepartamentoExceptionBadRequest
import rodriguez.daniel.exception.DepartamentoExceptionNotFound
import rodriguez.daniel.mappers.fromDTO
import rodriguez.daniel.mappers.toDTO
import rodriguez.daniel.repositories.departamento.IDepartamentoRepository
import rodriguez.daniel.repositories.empleado.IEmpleadoRepository
import java.util.UUID

@Single
class DepartamentoService(
    @Named("EmpleadoRepositoryCached")
    private val eRepo: IEmpleadoRepository,
    @Named("DepartamentoRepositoryCached")
    private val dRepo: IDepartamentoRepository,
) {
    suspend fun findDepartamentoById(id: UUID): DepartamentoDTO = withContext(Dispatchers.IO) {
        dRepo.findById(id)?.toDTO() ?: throw DepartamentoExceptionNotFound("Couldn't find departamento with id $id.")
    }

    suspend fun findAllDepartamentos(): List<DepartamentoDTO> = withContext(Dispatchers.IO) {
        val empleados = dRepo.findAll().toList()
        val response = mutableListOf<DepartamentoDTO>()
        empleados.forEach { response.add(it.toDTO()) }

        response
    }

    suspend fun saveDepartamento(entity: DepartamentoDTOcreacion): DepartamentoDTO = withContext(Dispatchers.IO) {
        dRepo.save(entity.fromDTO()).toDTO()
    }

    suspend fun deleteDepartamento(id: UUID): DepartamentoDTO = withContext(Dispatchers.IO) {
        val entity = dRepo.findById(id)
            ?: throw DepartamentoExceptionNotFound("Couldn't find departamento with id $id.")
        if (entity.toDTO().empleados.isNotEmpty())
            throw DepartamentoExceptionBadRequest("Cannot delete departamento. It has empleados attached to it.")

        dRepo.delete(id)?.toDTO() ?: throw DepartamentoExceptionBadRequest("Unable to delete departamento $id.")
    }
}