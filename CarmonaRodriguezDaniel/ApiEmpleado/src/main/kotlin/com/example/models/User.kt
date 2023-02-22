package com.example.models

data class User(
    val username: String,
    val password: String,
    val role: Role = Role.USER,
) {
    enum class Role {
        USER, ADMIN
    }
}
