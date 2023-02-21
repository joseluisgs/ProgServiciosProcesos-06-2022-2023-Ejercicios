package mendoza.js.models

import java.time.LocalDateTime
import java.util.UUID

data class Departamento(
    val id: UUID = UUID.randomUUID(),
    var nombre: String,
    var presupuesto: Double,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
    val deleted: Boolean = false
)