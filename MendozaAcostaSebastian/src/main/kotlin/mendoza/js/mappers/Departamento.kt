package mendoza.js.mappers

import mendoza.js.dto.DepartamentoCreateDto
import mendoza.js.dto.DepartamentoDto
import mendoza.js.entities.DepartamentoEntity
import mendoza.js.models.Departamento

fun Departamento.toDTO(): DepartamentoDto {
    return DepartamentoDto(
        id = this.id,
        nombre = this.nombre,
        presupuesto = this.presupuesto,
        metadata = DepartamentoDto.MetaData(
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            deleted = this.deleted
        )
    )
}

fun DepartamentoCreateDto.toModel(): Departamento {
    return Departamento(
        nombre = this.nombre,
        presupuesto = this.presupuesto
    )
}

fun DepartamentoEntity.toModel(): Departamento {
    return Departamento(
        id = this.id,
        nombre = this.nombre,
        presupuesto = this.presupuesto,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deleted = this.deleted
    )
}

fun Departamento.toEntity(): DepartamentoEntity {
    return DepartamentoEntity(
        id = this.id,
        nombre = this.nombre,
        presupuesto = this.presupuesto,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deleted = this.deleted
    )
}