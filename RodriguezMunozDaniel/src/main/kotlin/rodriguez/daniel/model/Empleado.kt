package rodriguez.daniel.model

import kotlinx.serialization.Serializable
import rodriguez.daniel.services.serializers.UUIDSerializer
import java.util.*

@Serializable
data class Empleado(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val email: String,
    val avatar: String,
    @Serializable(with = UUIDSerializer::class)
    val departamentoId: UUID
)