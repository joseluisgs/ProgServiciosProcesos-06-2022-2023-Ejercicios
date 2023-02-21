package rodriguez.daniel.model

import kotlinx.serialization.Serializable
import rodriguez.daniel.services.serializers.UUIDSerializer
import java.util.*

@Serializable
data class Departamento(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val presupuesto: Double = 0.0
)
