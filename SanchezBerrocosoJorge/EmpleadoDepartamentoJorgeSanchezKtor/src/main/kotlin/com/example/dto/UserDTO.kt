package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val uuid: String,
    val username: String,
    val password: String,
    val email: String,
) {
}

@Serializable
data class UserLoginDTO(
    val username: String,
    val password: String,
) {
}

@Serializable
data class UserRegisterDTO(
    val username: String,
    val password: String,
    val type: String,
) {
}

@Serializable
data class UserResponseDTO(
    val username: String,
    val type: String,
) {
}

