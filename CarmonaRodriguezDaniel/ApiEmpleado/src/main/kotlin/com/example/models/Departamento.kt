package com.example.models

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Departamento(
    var id: Long?,
    var uuid: String = UUID.randomUUID().toString(),
    var name: String,
) {
    fun toString(separator: String): String {
        return "$id$separator$uuid$separator$name"
    }
}
