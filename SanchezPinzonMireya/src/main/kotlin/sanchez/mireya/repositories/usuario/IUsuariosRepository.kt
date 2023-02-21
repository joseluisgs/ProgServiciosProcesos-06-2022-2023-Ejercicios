package sanchez.mireya.repositories.usuario

import sanchez.mireya.models.Usuario
import sanchez.mireya.repositories.CRUDRepository
import java.util.*

interface IUsuariosRepository : CRUDRepository<Usuario, UUID>{
}