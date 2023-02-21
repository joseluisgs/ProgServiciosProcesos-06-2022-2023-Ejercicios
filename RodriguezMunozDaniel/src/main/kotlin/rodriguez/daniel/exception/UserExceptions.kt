package rodriguez.daniel.exception

sealed class UserException(message: String) : RuntimeException(message)
class UserUnauthorizedException(message: String) : UserException(message)