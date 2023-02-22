package drodriguez.es.dto

import drodriguez.es.models.Empleado
import drodriguez.es.serializers.LocalDateTimeSerializer
import drodriguez.es.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class EmpleadoDto(
    val nombre: String,
    val email: String,
    val avatar: String,
    val departamentoId: String? = null
) {

}
