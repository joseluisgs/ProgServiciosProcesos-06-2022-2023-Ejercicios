package drodriguez.es.plugins

import drodriguez.es.services.DBServices
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureDataBase() {

    val dataBaseService: DBServices by inject()

    dataBaseService.initDataBaseService()
}