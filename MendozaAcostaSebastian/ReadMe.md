# Ejercicio propuesto para PSP: Empresa-Ktor-Rest
Pequeño proyecto que gestiona una empresa (departamentos y empleados) usando Ktor y tecnologías Kotlin para la asignatura Programación de Servicios y Procesos del IES Luis Vives(Leganés) 22/23
# Diseño
Para empezar a desarrollar dicho proyecto, podremos echar mano del asistente de [IntelliJ](https://www.jetbrains.com/es-es/idea/) para crear proyectos Ktor o usar el generador de proyectos vía web de [Ktor](https://ktor.io/).
A la hora de utilizar un almacenamiento de la información, se ha optado por la combinación de [H2](https://www.h2database.com/html/main.html) y [Kotysa](https://ufoss.org/kotysa/kotysa.html) reactiva.
# Estructura de proyecto
El proyecto se compone de tres modelos diferentes: Departamento, Empleado y Usuario. 
Cada clase tiene su correspondiente dto, entity, repository, etc. para poder hacer el CRUD final. En el caso de Usuario, se ha utilizado [JWT](https://jwt.io/) para el manejo de tokens y permisos. También se ha utilizado [Bcrypt](https://github.com/ToxicBakery/bcrypt-mpp) para codificar las contraseñas en la bbdd.
Para testear los endpoints de la API se ha usado [Postman](https://www.postman.com/).

Se ha usado [Koin](https://insert-koin.io/) para la inyección de dependencias y [Cache4K](https://github.com/ReactiveCircus/cache4k) para cachear la información consultada y volverla a recoger más rápido si lo volvemos a hacer.
# Uso
La aplicación puede usarse mediante [Docker](https://github.com/ToxicBakery/bcrypt-mpp) ejecutando un dokerfile o directamente desde el editor.