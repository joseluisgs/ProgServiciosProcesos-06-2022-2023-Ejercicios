package es.ruymi.models

import java.util.*


data class Departamento(
    val id: UUID = UUID.randomUUID(),
    var nombre: String,
    val presupuesto: Int
) {

}