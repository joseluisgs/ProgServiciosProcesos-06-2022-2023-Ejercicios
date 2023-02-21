package mendoza.js.entities

import org.ufoss.kotysa.h2.H2Table
import java.time.LocalDateTime
import java.util.*

object EmpleadoTable : H2Table<EmpleadoEntity>("empleados") {
    val id = uuid(EmpleadoEntity::id).primaryKey()
    val nombre = varchar(EmpleadoEntity::nombre, size = 100)
    val salario = doublePrecision(EmpleadoEntity::salario)
    val createdAt = timestamp(EmpleadoEntity::createdAt, "created_at")
    val updatedAt = timestamp(EmpleadoEntity::updatedAt, "updated_at")
    val deleted = boolean(EmpleadoEntity::deleted)
}

data class EmpleadoEntity(
    val id: UUID,
    val nombre: String,
    val salario: Double,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
    val deleted: Boolean = false
)