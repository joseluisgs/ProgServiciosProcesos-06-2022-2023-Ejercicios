package rodriguez.daniel

import io.ktor.server.application.*
import kotlinx.coroutines.*
import org.koin.ktor.ext.inject
import rodriguez.daniel.db.departamentos
import rodriguez.daniel.db.empleados
import rodriguez.daniel.db.users
import rodriguez.daniel.plugins.*
import rodriguez.daniel.services.departamento.DepartamentoService
import rodriguez.daniel.services.empleado.EmpleadoService
import rodriguez.daniel.services.user.UserService

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() = runBlocking {
    configureSerialization()
    configureKoin()
    configureValidation()
    configureStorage()
    configureSecurity()
    configureRouting()
    configureSwagger()

    val userService: UserService by inject()
    val departamentoService: DepartamentoService by inject()
    val empleadoService: EmpleadoService by inject()

    loadData(userService, departamentoService, empleadoService)
}

private suspend fun loadData(
    userService: UserService,
    departamentoService: DepartamentoService,
    empleadoService: EmpleadoService
) {
    users().forEach { userService.saveUser(it) }
    departamentos().forEach { departamentoService.saveDepartamento(it) }
    empleados().forEach { empleadoService.saveEmpleado(it) }
}
