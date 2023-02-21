package mendoza.js.models

import java.time.LocalDateTime
import java.util.*

data class Empleado(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    var salario: Double,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
    val deleted: Boolean = false
)