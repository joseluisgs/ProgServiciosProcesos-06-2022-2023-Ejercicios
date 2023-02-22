package drodriguez.es.services

import drodriguez.es.db.DataBase
import drodriguez.es.db.getDepartamentos
import drodriguez.es.db.getEmpleados
import drodriguez.es.db.getUsuarios
import drodriguez.es.mappers.toUser
import kotlinx.coroutines.runBlocking
import org.koin.core.annotation.Single

@Single
class DBServices(
    private val db: DataBase
) {
    private fun initializeDatabase() = runBlocking {
        getDepartamentos().forEach {
            db.listDepartamentos[it.id] = it
        }
        getEmpleados().forEach {
            db.listEmpleados[it.id] = it
        }
        getUsuarios().forEach {
            val user = it.toUser()
            db.listUsers[user.id] = user
        }
    }
    fun getLists() = db
    fun initDataBaseService() {
        initializeDatabase()
    }
    fun clearDataBaseService() {
        db.listDepartamentos.clear()
        db.listEmpleados.clear()
        db.listUsers.clear()
    }
}