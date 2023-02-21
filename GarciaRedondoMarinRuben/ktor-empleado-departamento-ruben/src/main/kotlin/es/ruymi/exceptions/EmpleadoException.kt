package es.ruymi.exceptions


sealed class EmpleadoException(message: String) : RuntimeException(message)
class EmpleadoNotFoundException(message: String) : EmpleadoException(message)
class EmpleadoBadRequestException(message: String) : EmpleadoException(message)
class EmpleadoUnauthorizedException(message: String) : EmpleadoException(message)
class EmpleadoForbiddenException(message: String) : EmpleadoException(message)

