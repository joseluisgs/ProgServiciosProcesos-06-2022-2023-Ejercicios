package drodriguez.es.models

import kotlinx.serialization.Contextual
import java.util.UUID

data class Departamento(
    val id: UUID = UUID.randomUUID(),
    val nombreDepartamento: String,
) {
}