package es.ruymi.models

import java.util.*

data class User(
    val id: UUID = UUID.randomUUID(),
    val correo: String,
    val usuario: String,
    val password: String,
    val rol: Rol = Rol.USER
) {
    enum class Rol {
        USER, ADMIN
    }
}