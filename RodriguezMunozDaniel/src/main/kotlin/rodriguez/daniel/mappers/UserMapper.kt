package rodriguez.daniel.mappers

import rodriguez.daniel.dto.UserDTO
import rodriguez.daniel.dto.UserDTOcreacion
import rodriguez.daniel.model.User
import rodriguez.daniel.services.utils.cipher
import java.util.*

fun User.toDTO() = UserDTO(email, role)
fun UserDTOcreacion.fromDTO() = User(id, email, cipher(password), role)