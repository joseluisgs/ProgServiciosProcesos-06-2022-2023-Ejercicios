package resa.mario.mappers

import com.example.dto.UserRegisterDTO
import com.example.dto.UserResponseDTO
import com.example.models.User
import org.mindrot.jbcrypt.BCrypt


fun User.toDTO(): UserResponseDTO {
    return UserResponseDTO(
        username = username,
        type = type,
    )
}


fun UserRegisterDTO.toUsuario(): User {
    return User(
        username = username,
        password = BCrypt.hashpw(password, BCrypt.gensalt(12)),
        type = type
    )
}