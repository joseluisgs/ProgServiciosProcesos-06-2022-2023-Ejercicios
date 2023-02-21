package resa.mario.plugins

import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import resa.mario.services.DataBaseService

fun Application.configureDataBase() {

    val dataBaseService: DataBaseService by inject()

    dataBaseService.initDataBaseService()
}