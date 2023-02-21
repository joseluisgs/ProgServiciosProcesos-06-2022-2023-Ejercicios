package com.ktordam.exceptions

sealed class UsuariosException(message: String): RuntimeException(message)
class UsuariosNotFoundException(message: String): UsuariosException(message)
class UsuariosBadRequestException(message: String): UsuariosException(message)
class UsuariosUnauthorizedException(message: String): UsuariosException(message)