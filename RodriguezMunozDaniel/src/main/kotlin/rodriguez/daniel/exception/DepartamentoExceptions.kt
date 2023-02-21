package rodriguez.daniel.exception

sealed class DepartamentoException(message: String) : RuntimeException(message)
class DepartamentoExceptionNotFound(message: String) : DepartamentoException(message)
class DepartamentoExceptionBadRequest(message: String) : DepartamentoException(message)