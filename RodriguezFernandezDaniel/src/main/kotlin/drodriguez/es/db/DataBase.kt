package drodriguez.es.db

import drodriguez.es.models.Departamento
import drodriguez.es.models.Empleado
import drodriguez.es.models.User
import org.koin.core.annotation.Single
import java.util.UUID
@Single
class DataBase {
    val listUsers = mutableMapOf<UUID, User>()
    val listEmpleados = mutableMapOf<UUID, Empleado>()
    val listDepartamentos = mutableMapOf<UUID, Departamento>()
}