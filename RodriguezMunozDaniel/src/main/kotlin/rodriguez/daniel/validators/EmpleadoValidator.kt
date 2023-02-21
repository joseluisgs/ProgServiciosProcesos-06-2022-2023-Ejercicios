package rodriguez.daniel.validators

import io.ktor.server.plugins.requestvalidation.*
import rodriguez.daniel.dto.EmpleadoDTOcreacion

fun RequestValidationConfig.empleadoValidation() {
    validate<EmpleadoDTOcreacion> { entity ->
        if (entity.nombre.isBlank())
            ValidationResult.Invalid("Name cannot be blank.")
        else if (entity.email.isBlank())
            ValidationResult.Invalid("Email cannot be blank.")
        else if (!entity.email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")))
            ValidationResult.Invalid("Invalid email.")
        else if (entity.nombre.length < 2)
            ValidationResult.Invalid("Name must at least be 2 characters long.")
        else ValidationResult.Valid
    }
}