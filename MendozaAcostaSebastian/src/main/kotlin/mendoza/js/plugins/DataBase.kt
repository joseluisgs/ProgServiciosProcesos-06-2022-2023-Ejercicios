package mendoza.js.plugins

import io.ktor.server.application.*
import mendoza.js.config.DataBaseConfig
import mendoza.js.service.database.DataBaseService
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureDataBase() {
    val dataBaseConfigParams = mapOf(
        "driver" to environment.config.property("database.driver").getString(),
        "protocol" to environment.config.property("database.protocol").getString(),
        "user" to environment.config.property("database.user").getString(),
        "password" to environment.config.property("database.password").getString(),
        "database" to environment.config.property("database.database").getString(),
        "initDatabaseData" to environment.config.property("database.initDatabaseData").getString()
    )

    val dataBaseConfig: DataBaseConfig = get { parametersOf(dataBaseConfigParams) }
    val dataBaseService: DataBaseService by inject()
    dataBaseService.initDataBaseService()
}