package mendoza.js.entities

import org.ufoss.kotysa.h2.H2Table
import java.time.LocalDateTime
import java.util.*

object DepartamentoTable : H2Table<DepartamentoEntity>("departamentos") {
    val id = uuid(DepartamentoEntity::id).primaryKey()
    val nombre = varchar(DepartamentoEntity::nombre, size = 100)
    val presupuesto = doublePrecision(DepartamentoEntity::presupuesto)
    val createdAt = timestamp(DepartamentoEntity::createdAt, "created_at")
    val updatedAt = timestamp(DepartamentoEntity::updatedAt, "updated_at")
    val deleted = boolean(DepartamentoEntity::deleted)
}

data class DepartamentoEntity(
    val id: UUID,
    val nombre: String,
    val presupuesto: Double,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
    val deleted: Boolean = false
)