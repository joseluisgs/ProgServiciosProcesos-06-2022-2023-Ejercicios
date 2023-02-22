package drodriguez.es.dto

import drodriguez.es.models.Departamento
import drodriguez.es.models.Empleado
import drodriguez.es.serializers.LocalDateTimeSerializer
import drodriguez.es.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class DepartamentoDto(
    val nombreDepartamento: String

)