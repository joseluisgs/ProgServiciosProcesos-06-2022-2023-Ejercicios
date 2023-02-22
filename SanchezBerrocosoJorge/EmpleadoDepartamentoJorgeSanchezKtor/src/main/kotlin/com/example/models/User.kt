package com.example.models

import java.util.UUID

data class User(
    val id: UUID = UUID.randomUUID(),
    val username: String,
    val password: String,
    val type: String = Type.USER.name

) {


    enum class Type(){
        USER, ADMIN
    }

    override fun toString(): String {
        return "User(id=$id, username='$username', password='$password', type='$type')"
    }
}