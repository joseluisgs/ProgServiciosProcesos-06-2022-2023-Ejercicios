# Generacion de una imagen docker
FROM gradle:7-jdk17 as build

# Se copia el codigo fuente de la aplicacion; el directorio actual
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

# Generamos la imagen que ejecuta la aplicacion
FROM openjdk:17-jdk-slim-buster
EXPOSE 6969:6969
# Directorio donde se almacena la aplicacion
RUN mkdir /app
# Copiamos el jar
COPY --from=build /home/gradle/src/build/libs/EmpleadoDepartamentoKtor_4-all.jar /app/empleadoDepartamentoKtor.jar
# Ejecutamos la aplicacion
ENTRYPOINT ["java", "-jar", "/app/empleadoDepartamentoKtor.jar"]