package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginDto(val username: String, val password: String)