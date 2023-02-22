package drodriguez.es.models

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val password: String,
    val rol: String = Rol.USER.name
) {
    enum class Rol {
        USER, JEFE
    }
}