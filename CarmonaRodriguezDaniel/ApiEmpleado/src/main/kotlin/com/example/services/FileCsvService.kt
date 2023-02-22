package com.example.services

import com.example.models.Departamento
import com.example.models.Empleado
import org.koin.core.annotation.Single
import java.io.File

@Single
class FileCsvService {
    private val empleados = mutableMapOf(
        1L to Empleado(id = 1L, name = "Dani", departamento = 1L, available = true),
        2L to Empleado(id = 2L, name = "Nuria", departamento = 1L, available = false),
        3L to Empleado(id = 3L, name = "Aura", departamento = 2L, available = true)
    )
    private val departamentos = mutableMapOf(
        1L to Departamento(id = 1L, name = "Desarrollo"),
        2L to Departamento(id = 2L, name = "Administración")
    )
    private val dir: String = System.getProperty("user.dir") + File.separator + "data"
    private val fileEmpleado: String = dir + File.separator + "dataEmpleado.csv"
    private val fileDepartamento: String = dir + File.separator + "dataDepartamento.csv"

    init {
        if (!File(dir).exists()) {
            File(dir).mkdir()
            File(fileEmpleado).createNewFile()
            File(fileDepartamento).createNewFile()
            writeFileEmpleado(empleados)
            writeFileDepartamento(departamentos)
        }
    }

    fun writeFileEmpleado(empleados: MutableMap<Long, Empleado>) {
        File(fileEmpleado).bufferedWriter().use {
            it.write("id;uuid;name;departamentoID;available")
            empleados.forEach { key ->
                it.append("\n" + key.value.toString(";"))
            }
        }
    }

    fun writeFileDepartamento(departamentos: MutableMap<Long, Departamento>) {
        File(fileDepartamento).bufferedWriter().use {
            it.write("id;uuid;name")
            departamentos.forEach { key ->
                it.append("\n" + key.value.toString(";"))
            }
        }
    }

    fun writeEmpleado(empleado: Empleado) {
        File(fileEmpleado).appendText("\n" + empleado.toString(";"))
    }

    fun writeDepartamento(departamento: Departamento) {
        File(fileDepartamento).appendText("\n" + departamento.toString(";"))
    }

    fun readFileEmpleado(): MutableMap<Long, Empleado> {
        val empleados = mutableMapOf<Long, Empleado>()
        File(fileEmpleado).readLines().drop(1).map { it.split(";") }
            .map { field ->
                val empleado = Empleado(
                    field[0].toLong(),
                    field[1],
                    field[2],
                    field[3].toLong(),
                    field[4].toBoolean()
                )
                empleados.put(empleado.id!!, empleado)
            }
        return empleados
    }

    fun readFileDepartamento(): MutableMap<Long, Departamento> {
        val departamentos = mutableMapOf<Long, Departamento>()
        File(fileDepartamento).readLines().drop(1).map { it.split(";") }
            .map { field ->
                val departamento = Departamento(
                    field[0].toLong(),
                    field[1],
                    field[2]
                )
                departamentos.put(departamento.id!!, departamento)
            }
        return departamentos
    }

    fun clean() {
        println("Limpiando")
        writeFileDepartamento(departamentos)
        writeFileEmpleado(empleados)
    }
}