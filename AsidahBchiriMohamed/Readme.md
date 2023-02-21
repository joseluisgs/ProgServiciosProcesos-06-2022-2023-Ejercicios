## Api Empleado/Departamento
### Diseño
#### Datos
En este ejercicio no vamos a utilizar una base de datos para guardar nuestros datos. Usaremos una lista mutable.
Guardaremos en los empleados su nombre y apellido, su email, su sueldo y su departamento y su rol.
Para los departamentos guardamos el nombre del departamento y la lista de trabajadores que trabajan en el.

#### Inyección de dependencias
Para la inyección de dependencias he utilizado Koin para Ktor instalandolo como un plugin y utilizando anotaciones para configurar los modulos.

#### Seguridad
Para la seguridad se utiliza un token JWT que se genera con el correo y el rol de un trabajador que se logea con su mail. El token tendrá una expiración de una hora y le dara acceso si es admin a crear y eliminar empleados así como departamentos.

#### Testeo
Para el testeo he utilizado los tests de JUnit 5 y de Postman (se pueden encontrar en la carpeta postman).

#### Almacenamiento
Para el almacenamiento se suben los archivos en binario utilizando el sistema de almacenaje de Ktor.
### Autor
Mohamed Asidah Bchiri