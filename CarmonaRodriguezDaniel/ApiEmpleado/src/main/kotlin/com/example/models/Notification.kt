package com.example.models

import joseluisgs.es.serializers.LocalDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Notification<T>(
    val entity: String,
    val tipo: Tipo,
    val id: Long,
    val data: T,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime? = LocalDateTime.now(),
) {
    enum class Tipo {
        CREATE, UPDATE, DELETE
    }
}
