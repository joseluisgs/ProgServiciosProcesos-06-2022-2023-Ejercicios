package com.example.exceptions

import com.example.models.Empleado

sealed class EmpleadoException(message : String) : Exception(message)

class EmpleadoNotFound(message: String) : EmpleadoException(message)
class EmpleadoBadRequest(message: String) : EmpleadoException(message)
class EmpleadoUnathorized(message: String) : EmpleadoException(message)

