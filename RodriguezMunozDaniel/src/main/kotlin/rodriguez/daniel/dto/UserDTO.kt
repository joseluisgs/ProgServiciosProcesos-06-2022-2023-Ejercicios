package rodriguez.daniel.dto

import kotlinx.serialization.Serializable
import rodriguez.daniel.model.Role
import rodriguez.daniel.services.serializers.UUIDSerializer
import java.util.UUID

@Serializable
data class UserDTOcreacion(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val password: String,
    val role: Role = Role.EMPLEADO
)

@Serializable
data class UserDTOlogin(
    val email: String,
    val password: String
)

@Serializable
data class UserDTO(
    val email: String,
    val role: Role = Role.EMPLEADO
)

@Serializable
data class UserDTOandToken(
    val user: UserDTO,
    val token: String
)