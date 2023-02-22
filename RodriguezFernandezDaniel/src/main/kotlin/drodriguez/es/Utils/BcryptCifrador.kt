package drodriguez.es.Utils

import com.toxicbakery.bcrypt.Bcrypt
import io.ktor.util.*

object BcryptCifrador {
    private const val SALT_LENGTH = 12
    fun cifrar(password: String): String {
        return Bcrypt.hash(password, SALT_LENGTH).decodeToString()
    }
}