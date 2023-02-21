package resa.mario.db

import org.koin.core.annotation.Single
import resa.mario.models.Departamento
import resa.mario.models.Empleado
import resa.mario.models.Usuario
import java.util.UUID

/**
 * Clase que emula una base de datos, cada mapa representa una tabla.
 *
 */
@Single
class Database {

    val tableUsuarios = mutableMapOf<UUID, Usuario>()
    val tableDepartamentos = mutableMapOf<UUID, Departamento>()
    val tableEmpleados = mutableMapOf<UUID, Empleado>()

}