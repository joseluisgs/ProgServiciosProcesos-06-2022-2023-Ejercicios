package com.example.models

import java.util.UUID

class Empleado(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val email: String,
    val departamentoId: UUID?,
    var avatar: String?,
) {
    override fun toString(): String {
        return "Empleado(uuid=$id, name='$name', email='$email', departamentoId=$departamentoId, avatar='$avatar')"
    }
}