package es.ruymi.exceptions


sealed class DepartamentoException(message: String) : RuntimeException(message)
class DepartamentoNotFoundException(message: String) : DepartamentoException(message)
class DepartamentoBadRequestException(message: String) : DepartamentoException(message)
class DepartamentoUnauthorizedException(message: String) : DepartamentoException(message)
class DepartamentoForbiddenException(message: String) : DepartamentoException(message)

