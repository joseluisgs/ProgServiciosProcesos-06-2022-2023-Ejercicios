package com.example.plugins

import com.example.validators.departValidator
import com.example.validators.emplValidator
import com.example.validators.loginValidator
import com.example.validators.usuarioValidator
import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*

fun Application.configValidator(){
    install(RequestValidation){
        departValidator()
        emplValidator()
        usuarioValidator()
        loginValidator()
    }
}