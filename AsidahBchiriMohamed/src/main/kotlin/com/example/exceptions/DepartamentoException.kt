package com.example.exceptions

sealed class DepartamentoException(message: String) : Exception(message)
class DepartamentoNotFound(message: String) : DepartamentoException(message)
class DepartamentoBadRequest(message: String) : DepartamentoException(message)
class DepartamentoUnauthorized(message: String) : DepartamentoException(message)