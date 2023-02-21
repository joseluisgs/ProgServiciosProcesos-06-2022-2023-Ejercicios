package mendoza.js.models

import java.time.LocalDateTime
import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val nombre: String,
    val email: String,
    val username: String,
    val password: String,
    val avatar: String,
    val role: Role = Role.USER,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false
) {
    enum class Role {
        USER, ADMIN
    }
}