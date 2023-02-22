package drodriguez.es.models

import java.time.LocalDateTime
import java.util.*

data class Empleado(
    val id: UUID = UUID.randomUUID(),
    val nombre : String,
    val email: String,
    var avatar: String,
    var departamentoId: UUID? = null,

    ){

}
