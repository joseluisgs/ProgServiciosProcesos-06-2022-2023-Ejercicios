package rodriguez.daniel.validators

import io.ktor.server.plugins.requestvalidation.*
import rodriguez.daniel.dto.DepartamentoDTOcreacion

fun RequestValidationConfig.departamentoValidation() {
    validate<DepartamentoDTOcreacion> { entity ->
        if (entity.nombre.isBlank())
            ValidationResult.Invalid("Name cannot be blank.")
        else if (entity.presupuesto <= 0.0)
            ValidationResult.Invalid("Budget cannot be 0 or negative.")
        else ValidationResult.Valid
    }
}