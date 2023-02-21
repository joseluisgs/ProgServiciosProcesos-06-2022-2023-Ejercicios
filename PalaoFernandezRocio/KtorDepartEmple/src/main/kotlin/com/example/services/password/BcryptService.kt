package com.example.services.password

import org.koin.core.annotation.Single
import org.mindrot.jbcrypt.BCrypt

@Single
class BcryptService {
    fun encryptPassword(password: String):String{
        return BCrypt.hashpw(password, BCrypt.gensalt(12))
    }

    fun verifyPassword(password: String, passwordEncrypt:String ):Boolean{
        return BCrypt.checkpw(password, passwordEncrypt)
    }
}