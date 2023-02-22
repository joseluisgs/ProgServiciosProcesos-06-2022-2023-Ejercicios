package es.ruymi.dto

import es.ruymi.models.User
import kotlinx.serialization.Serializable


@Serializable
data class UserDTO(
    val id: String,
    val usuario: String,
    val correo: String,
    val password: String,
    val rol: String
)

@Serializable
data class UserUpdateDto(
    val correo: String,
    val usuario: String,
)

@Serializable
data class UserCreateDto(
    val correo: String,
    val usuario: String,
    val password: String,
    val rol: User.Rol? = User.Rol.USER,
)

@Serializable
data class UserLoginDto(
    val usuario: String,
    val password: String
)

@Serializable
data class UserWithTokenDto(
    val usuario: UserDTO,
    val token: String
)