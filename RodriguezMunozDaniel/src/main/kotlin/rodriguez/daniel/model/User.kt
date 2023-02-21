package rodriguez.daniel.model

import kotlinx.serialization.Serializable
import rodriguez.daniel.services.serializers.UUIDSerializer
import java.util.*

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val password: String,
    val role: Role = Role.EMPLEADO
)

enum class Role {
    EMPLEADO,ADMIN
}