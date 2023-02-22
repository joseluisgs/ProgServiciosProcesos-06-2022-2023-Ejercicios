package drodriguez.es.mappers

import drodriguez.es.Utils.BcryptCifrador
import drodriguez.es.dto.UserCreateDto
import drodriguez.es.dto.UserResDto
import drodriguez.es.models.User

fun User.toDto(): UserResDto {
    return UserResDto(
        username = username,
        rol = rol
    )
}

fun UserCreateDto.toUser(): User {
    return User(
        username = username,
        password = BcryptCifrador.cifrar(password),
        rol = rol
    )
}