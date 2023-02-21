package sanchez.mireya.services

import com.toxicbakery.bcrypt.Bcrypt

object Cifrador {
    private const val BRCYPT_SALT = 12

    fun cipher(message: String): String {
        return Bcrypt.hash(message, BRCYPT_SALT).decodeToString()
    }
}