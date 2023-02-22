package com.example.models

import java.util.UUID

class Departamento(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val presupuesto: Double,

) {
    override fun toString(): String {
        return "Departamento(uuid=$id, nome='$name', presupuesto=$presupuesto)"
    }
}