package drodriguez.es.dto

import kotlinx.serialization.Serializable

// DTO Login User
@Serializable
data class UserLoginDto(
    val username: String,
    val password: String
)
// DTO Create User -- es el mismo que el de update
@Serializable
data class UserCreateDto(
    val username: String,
    val password: String,
    val rol: String
)

// DTO Update User -- es el mismo que el de create
@Serializable
data class UserUpdateDto(
    val username: String,
    val password: String,
    val rol: String
)

// DTO Datos visibles para el usuario, de esta forma no mostramos datos personales o de seguridad como la password
@Serializable
data class UserResDto(
    val username: String,
    val rol: String
)
