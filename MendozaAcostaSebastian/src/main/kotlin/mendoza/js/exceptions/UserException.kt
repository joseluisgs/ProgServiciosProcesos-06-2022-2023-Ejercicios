package mendoza.js.exceptions

sealed class UserException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : UserException(message)
class UserBadRequestException(message: String) : UserException(message)
class UserUnauthorizedException(message: String) : UserException(message)
