package mendoza.js.dto

import joseluisgs.es.serializers.LocalDateTimeSerializer
import joseluisgs.es.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.*

@Serializable
data class EmpleadoCreateDto(
    val nombre: String,
    val salario: Double
)

@Serializable
data class EmpleadoDto(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID? = null,
    val nombre: String,
    var salario: Double,
    val metadata: MetaData? = null
) {
    @Serializable
    data class MetaData(
        @Serializable(with = LocalDateTimeSerializer::class)
        val createdAt: LocalDateTime? = LocalDateTime.now(),
        @Serializable(with = LocalDateTimeSerializer::class)
        val updatedAt: LocalDateTime? = LocalDateTime.now(),
        val deleted: Boolean = false
    )
}