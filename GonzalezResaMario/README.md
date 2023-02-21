# Ejercicio opcional PSP: KTOR-API-REST REACTIVO

Servicio "Departamento-Empleado" con Usuarios, para la asignatura Programación de Procesos y Servicios
del IES Luis Vives (Leganés) curso 22/23.

## Índice

- [Diseño](#diseño)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Funcionamiento de la aplicación](#funcionamiento-de-la-aplicación)
- [Autor](#autor)

# Diseño

## Introducción

Para la generación del proyecto base se ha
usado [Ktor Project Generator](https://start.ktor.io/#/settings?name=ktor-sample&website=example.com&artifact=com.example.ktor-sample&kotlinVersion=1.8.10&ktorVersion=2.2.3&buildSystem=GRADLE_KTS&engine=NETTY&configurationIn=HOCON&addSampleCode=true&plugins=)

Este ejercicio se basa en el enunciado de un problema propuesto por el profesor.

Este mismo se puede hallar en la [documentation](documentation).

## Configuración de la base de datos

En este caso, y a modo de practicar algo distinto, he usado mapas; igualmente
tengo ejemplos usando [Kotysa](https://ufoss.org/kotysa/kotysa.html) y [H2](https://www.h2database.com/html/main.html).

- [Ejemplo-Avanzado](https://github.com/Mario999X/EmpleadoDepartamentoKtorAvanzado)
- [Ejemplo-Medio](https://github.com/Mario999X/EmpleadoDepartamentoKtorMedio)

De todas formas, y respetando el diseño mostrado en esos ejemplos, he tratado de hacer las operaciones
de la manera más parecida posible, esto puede verse en los repositorios. Asi, da la sensación de estar trabajando con
una base de datos real.

# Estructura del proyecto

El proyecto se encuentra estructurado en distintos paquetes para una mejor legibilidad.

## Documentación

Las clases se encuentran comentadas con KDoc, más la implementación
de [Dokka](https://kotlinlang.org/docs/dokka-introduction.html), que permite visualizar la documentación del programa en
un archivo *html*

Además, se ha aplicado Swagger y OpenApi para la documentación de los *end points*; se ha usado la
librería [Ktor Swagger-UI](https://github.com/SMILEY4/ktor-swagger-ui)

# Funcionamiento de la aplicación

Contamos con una clase principal, *Application*, en ella llamamos a las funciones principales de los plugins usados
en la aplicación.

## Koin

Se ha usado [Koin](https://insert-koin.io/) con [Ksp](https://kotlinlang.org/docs/ksp-overview.html) para la inyección
de dependencias.

## Cache

Se ha usado [Cache4k](https://github.com/ReactiveCircus/cache4k) para cachear las entidades solicitadas en el
enunciado del problema: *departamento* y *empleado*.

## Seguridad

Se ha aplicado un sistema de tokens [JWT-ktor](https://ktor.io/docs/jwt.html) para asegurar las rutas que necesiten
verificación y el uso de [Bcrypt](https://github.com/ToxicBakery/bcrypt-mpp) para garantizar la seguridad de
las contraseñas de los usuarios almacenados.

## Almacenamiento

Se ha implantado un sistema de almacenamiento propio de *Ktor* para la subida de ficheros en binario.

Estos se pueden probar con sus propias rutas o bien actualizando el avatar de un empleado.

## Ejecución

Una vez se encuentre en ejecución, se recomienda el uso de un cliente que permite recibir y enviar *request-response*,
por ejemplo, [Postman](https://www.postman.com/) o el plugin [Thunder Client](https://www.thunderclient.com/)
en [VSC](https://code.visualstudio.com/)

## Despliegue

La aplicación se puede lanzar de tres formas distintas: *jar*, *aplicación* y *docker*

## Tests

Se han realizado test con *Postman* (E2E), se puede ver en el directorio [Postman](postman)

Además, se han testeado los repositorios, los servicios y las rutas, usando [JUnit 5](https://junit.org/junit5/) y
aplicando [Mockk](https://mockk.io/)

# Autor

[Mario Resa](https://github.com/Mario999X)
