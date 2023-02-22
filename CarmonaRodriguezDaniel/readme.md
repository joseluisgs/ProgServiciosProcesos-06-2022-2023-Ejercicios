# Ejercicio Api Departamento - Empleado con Ktor
## Creado por: Daniel Carmona Rodriguez

---
### Api
Esta creada con Ktor.
### Inyección de dependencias
Se utiliza koin con anotaciones.
### Repositorios
Funcionan mediante un mapa de la id y clase utilazada.
### Almacenamiento
Se almacenan los datos en un csv y se leen del mismo csv.
Todo funciona mediante un servicio para controlar el almacenamiento en el fichero.
### Seguridad
Se utiliza JWT y SSL para la seguridad. Al igual que se utiliza BCrypt para cifrar las contraseñas.
### Test
Adjuntado ficheros de postman donde se comprueba el funcionamiento de la api, y también testeado con JUnit la ruta de empleados.
### WebSockets
Implementación de tiempo real para cada actualización de departamento y/o empleado.
### Storage
Almacenamiento de imagenes y obtención de las mismas.

---
## EndPoints


### Empleado
#### Sin autenticar:
- GetAll: /empleados
- GetById: /empleados/{id}
- GetDepartamentoById: /empleados/{id}/departamento
#### Con autenticación:
- Post: /empleados
- Put: /empleados/{id}
- Delete: /empleados/{id}
#### WebSocket
- WS: /updates/empleados


### Departamento
#### Sin autenticar:
- GetAll: /departamentos
- GetById: /departamentos/{id}
- GetEmpleadosById: /departamentos/{id}/empleados
#### Con autenticación:
- Post: /departamentos
- Put: /departamentos/{id}
#### WebSocket
- WS: /updates/departamentos


### User
#### Sin autenticar:
- PostLogin: /login
- PostRegister: /register


### Storage
#### Sin autenticar:
- Post: /storage
- Get: /storage/{fileName}

