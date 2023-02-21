package es.ruymi.mappers

import es.ruymi.dto.*
import es.ruymi.models.User
import java.util.*

fun User.toDto(): UserDTO {
    return UserDTO(
        id = id.toString(),
        usuario = usuario,
        correo = correo,
        password = password,
        rol = rol.toString()
    )
}

fun UserDTO.toEntity(): User {
    return User(
        id = UUID.fromString(id),
        correo = correo,
        usuario = usuario,
        password = password,
        rol = User.Rol.valueOf(rol)
    )
}
fun UserCreateDto.toEntity(): User {
    return User(
        id = UUID.randomUUID(),
        correo = correo,
        usuario = usuario,
        password = password,
        rol = rol!!
    )
}


