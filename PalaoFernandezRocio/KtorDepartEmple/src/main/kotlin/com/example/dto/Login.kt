package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    var email: String,
    var password:String
)

@Serializable
data class TokenDto(
    var token: String
)