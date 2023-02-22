package com.example.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Empleado(
    var id: Long?,
    var uuid: String = UUID.randomUUID().toString(),
    var name: String,
    var departamento: Long?,
    var available: Boolean,
) {
    fun toString(separator: String): String {
        return "$id$separator$uuid$separator$name$separator$departamento$separator$available"
    }
}
