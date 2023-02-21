package sanchez.mireya.models

import java.time.LocalDateTime
import java.util.*

data class Usuario(
    val id: UUID,
    val username: String,
    val password: String,
    var role: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

enum class Role {
    USER, ADMIN
}