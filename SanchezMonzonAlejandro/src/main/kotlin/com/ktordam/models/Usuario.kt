package com.ktordam.models

import java.time.LocalDateTime
import java.util.*

data class Usuario(
    val uuid: UUID = UUID.randomUUID(),
    val username: String,
    val password: String,
    var role: String = Role.USER.name,

    val createdAt: LocalDateTime = LocalDateTime.now(),
) {
    enum class Role {
        USER, ADMIN
    }
}