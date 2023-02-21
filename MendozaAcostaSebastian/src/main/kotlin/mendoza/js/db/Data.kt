package mendoza.js.db

import mendoza.js.models.Departamento
import mendoza.js.models.Empleado
import mendoza.js.models.User
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun getEmpleadosInit() = listOf(
    Empleado(
        nombre = "Alfredo",
        salario = 1600.0,
    ), Empleado(
        nombre = "Sandra",
        salario = 2200.0,
    ), Empleado(
        nombre = "María",
        salario = 4200.0,
    ), Empleado(
        nombre = "Sebastián",
        salario = 3200.0,
    )
)

fun getDepartamentosInit() = listOf(
    Departamento(
        nombre = "Base de datos",
        presupuesto = 10000.0
    ),
    Departamento(
        nombre = "Programación",
        presupuesto = 10000.0
    ),
)

fun getUsuariosInit() = listOf(
    User(
        id = UUID.fromString("b39a2fd2-f7d7-405d-b73c-b68a8dedbcdf"),
        nombre = "Andrés Rodriguez",
        username = "ander",
        email = "andres@rodriguez.com",
        password = BCrypt.hashpw("ander1234", BCrypt.gensalt(12)),
        avatar = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        role = User.Role.ADMIN
    ),
    User(
        id = UUID.fromString("c53062e4-31ea-4f5e-a99d-36c228ed01a3"),
        nombre = "Lucía García",
        username = "luga",
        email = "lucia@garcia.com",
        password = BCrypt.hashpw("luga1234", BCrypt.gensalt(12)),
        avatar = "https://upload.wikimedia.org/wikipedia/commons/f/f4/User_Avatar_2.png",
        role = User.Role.USER
    )
)