package mendoza.js.mappers

import mendoza.js.dto.EmpleadoCreateDto
import mendoza.js.dto.EmpleadoDto
import mendoza.js.entities.EmpleadoEntity
import mendoza.js.models.Empleado

fun Empleado.toDTO(): EmpleadoDto {
    return EmpleadoDto(
        id = this.id,
        nombre = this.nombre,
        salario = this.salario,
        metadata = EmpleadoDto.MetaData(
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            deleted = this.deleted
        )
    )
}

fun EmpleadoCreateDto.toModel(): Empleado {
    return Empleado(
        nombre = this.nombre,
        salario = this.salario
    )
}

fun EmpleadoEntity.toModel(): Empleado {
    return Empleado(
        id = this.id,
        nombre = this.nombre,
        salario = this.salario,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deleted = this.deleted
    )
}

fun Empleado.toEntity(): EmpleadoEntity {
    return EmpleadoEntity(
        id = this.id,
        nombre = this.nombre,
        salario = this.salario,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        deleted = this.deleted
    )
}