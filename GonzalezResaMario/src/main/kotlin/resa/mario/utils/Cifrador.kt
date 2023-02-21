package resa.mario.utils

import com.toxicbakery.bcrypt.Bcrypt

/**
 * Clase objeto que se encarga de cifrar con [Bcrypt] las claves de los usuarios almacenados en la base de datos
 */
object Cifrador {

    private const val BRCYPT_SALT = 12

    fun cipher(password: String): String {
        return Bcrypt.hash(password, BRCYPT_SALT).decodeToString()
    }

}