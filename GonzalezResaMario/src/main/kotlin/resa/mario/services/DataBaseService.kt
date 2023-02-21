package resa.mario.services

import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Single
import resa.mario.db.Database
import resa.mario.db.getDepartamentos
import resa.mario.db.getEmpleados
import resa.mario.db.getUsuarios
import resa.mario.mappers.toUsuario

/**
 * Servicio de la base de datos, se encarga de introducir los datos de base y de poder realizar operaciones desde los
 * repositorios.
 *
 * @property database
 */
@Single
class DataBaseService(
    private val database: Database
) {

    private fun initData() = runBlocking {

        getDepartamentos().forEach {
            database.tableDepartamentos[it.id] = it
        }

        getEmpleados().forEach {
            database.tableEmpleados[it.id] = it
        }

        getUsuarios().forEach {
            val user = it.toUsuario()
            database.tableUsuarios[user.id] = user
        }

    }

    fun getTables() = database

    fun initDataBaseService() {
        println("Cargando datos")

        initData()
    }

    fun clearDataBaseService() {
        database.tableDepartamentos.clear()
        database.tableEmpleados.clear()
        database.tableUsuarios.clear()
    }
}