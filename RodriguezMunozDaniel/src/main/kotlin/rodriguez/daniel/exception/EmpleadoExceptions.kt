package rodriguez.daniel.exception

sealed class EmpleadoException(message: String) : RuntimeException(message)
class EmpleadoExceptionNotFound(message: String) : EmpleadoException(message)
class EmpleadoExceptionBadRequest(message: String) : EmpleadoException(message)