package mendoza.js.entities

import org.ufoss.kotysa.h2.H2Table
import java.time.LocalDateTime
import java.util.UUID

object UsersTable : H2Table<UserEntity>("usuarios") {
    val id = uuid(UserEntity::id).primaryKey()
    val nombre = varchar(UserEntity::nombre, size = 100)
    val email = varchar(UserEntity::email, size = 100)
    val username = varchar(UserEntity::username, size = 50)
    val password = varchar(UserEntity::password, size = 100)
    val avatar = varchar(UserEntity::avatar, size = 100)
    val role = varchar(UserEntity::role, size = 100)
    val createdAt = timestamp(UserEntity::createdAt, "created_at")
    val updatedAt = timestamp(UserEntity::updatedAt, "updated_at")
    val deleted = boolean(UserEntity::deleted)
}

data class UserEntity(
    val id: UUID,
    val nombre: String,
    val email: String,
    val username: String,
    val password: String,
    val avatar: String,
    val role: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val deleted: Boolean = false
)