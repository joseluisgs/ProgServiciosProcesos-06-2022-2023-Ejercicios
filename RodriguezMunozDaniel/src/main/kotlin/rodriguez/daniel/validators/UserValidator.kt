package rodriguez.daniel.validators

import io.ktor.server.plugins.requestvalidation.*
import rodriguez.daniel.dto.UserDTOcreacion
import rodriguez.daniel.dto.UserDTOlogin

fun RequestValidationConfig.userValidation() {
    validate<UserDTOcreacion> { entity ->
        if (entity.email.isBlank())
            ValidationResult.Invalid("Email cannot be blank.")
        else if (!entity.email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")))
            ValidationResult.Invalid("Invalid email.")
        else if (entity.password.length < 7 || entity.password.isBlank())
            ValidationResult.Invalid("Password must at least be 7 characters long.")
        else ValidationResult.Valid
    }

    validate<UserDTOlogin> { entity ->
        if (entity.email.isBlank())
            ValidationResult.Invalid("Email cannot be blank.")
        else if (!entity.email.matches(Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")))
            ValidationResult.Invalid("Invalid email.")
        else if (entity.password.length < 7 || entity.password.isBlank())
            ValidationResult.Invalid("Password must at least be 7 characters long.")
        else ValidationResult.Valid
    }
}