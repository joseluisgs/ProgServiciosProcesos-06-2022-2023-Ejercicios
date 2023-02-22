# Ejercicio Ktor Departamento Empleado
## Autor
    Jorge Sánchez Berrocoso
## Enunciado
Crear una API REST con KTOR totalmente reactiva. Que maneje Departamentos (id, nombre, presupuesto) y - Empleado (id, nombre, email, avatar)
y Departamento debe estar cacheado y tendrá las operaciones CRUD completas.

### Inyección de dependencias
Se ha utilizado Koin con Anotaciones.

### Caché
Se ha utiliizado Cache4k.

## Seguridad 🔒
Se ha utilizado JWT para la seguridad de la API con Ktor. También se ha utilizado Bcrypt
para codificar las contraseñas en la bbdd.

### Test
Testeado con Postman y JUnit5

