package rodriguez.daniel.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import rodriguez.daniel.validators.departamentoValidation
import rodriguez.daniel.validators.empleadoValidation
import rodriguez.daniel.validators.userValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        empleadoValidation()
        userValidation()
        departamentoValidation()
    }
}