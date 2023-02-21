package mendoza.js.service.database

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactoryOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mendoza.js.config.DataBaseConfig
import mendoza.js.db.getDepartamentosInit
import mendoza.js.db.getEmpleadosInit
import mendoza.js.db.getUsuariosInit
import mendoza.js.entities.DepartamentoTable
import mendoza.js.entities.EmpleadoTable
import mendoza.js.entities.UsersTable
import mendoza.js.mappers.toEntity
import mu.KotlinLogging
import org.koin.core.annotation.Single
import org.ufoss.kotysa.H2Tables
import org.ufoss.kotysa.r2dbc.sqlClient
import org.ufoss.kotysa.tables

private val logger = KotlinLogging.logger { }
@Single
class DataBaseService(
    private val dataBaseConfig: DataBaseConfig
) {
    private val connectionOptions = ConnectionFactoryOptions.builder()
        .option(ConnectionFactoryOptions.DRIVER, dataBaseConfig.driver)
        .option(ConnectionFactoryOptions.PROTOCOL, dataBaseConfig.protocol)
        .option(ConnectionFactoryOptions.USER, dataBaseConfig.user)
        .option(ConnectionFactoryOptions.PASSWORD, dataBaseConfig.password)
        .option(ConnectionFactoryOptions.DATABASE, dataBaseConfig.database)
        .build()

    val client = ConnectionFactories
        .get(connectionOptions)
        .sqlClient(getTables())

    val initData get() = dataBaseConfig.initDatabaseData

    fun initDataBaseService() {
        logger.debug { "Iniciando servicio de BBDD: ${dataBaseConfig.database}" }
        createTables()
        if (initData) {
            logger.debug { "Iniciando datos..." }
            createTables()
            initDataBaseData()
        }
    }

    private fun getTables(): H2Tables {
        return tables()
            .h2(
                UsersTable,
                DepartamentoTable,
                EmpleadoTable
            )
    }

    private fun createTables() = runBlocking {
        val scope = CoroutineScope(Dispatchers.IO)
        logger.debug { "Creando tablas..." }
        scope.launch {
            client createTableIfNotExists UsersTable
            client createTableIfNotExists DepartamentoTable
            client createTableIfNotExists EmpleadoTable
        }
    }

    fun clearDataBaseData() = runBlocking {
        logger.debug { "Borrando datos..." }
        try {
            client deleteAllFrom UsersTable
            client deleteAllFrom DepartamentoTable
            client deleteAllFrom EmpleadoTable
        } catch (_: Exception) {

        }
    }

    fun initDataBaseData() = runBlocking {
        logger.debug { "Creando datos..." }

        logger.debug { "Creando usuarios..." }
        getUsuariosInit().forEach {
            client insert it.toEntity()
        }

        logger.debug { "Creando departamentos..." }
        getDepartamentosInit().forEach {
            client insert it.toEntity()
        }

        logger.debug { "Creando empleados..." }
        getEmpleadosInit().forEach {
            client insert it.toEntity()
        }
    }
}