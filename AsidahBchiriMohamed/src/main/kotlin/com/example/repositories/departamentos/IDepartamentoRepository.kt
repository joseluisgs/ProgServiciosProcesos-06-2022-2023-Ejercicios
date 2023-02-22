package com.example.repositories.departamentos

import com.example.models.Departamento
import com.example.repositories.ICRUD
import java.util.UUID

interface IDepartamentoRepository : ICRUD<Departamento, UUID>{
}