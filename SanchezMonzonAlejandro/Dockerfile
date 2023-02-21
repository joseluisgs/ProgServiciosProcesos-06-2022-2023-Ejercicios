FROM gradle:7-jdk17 as build

COPY --chown=gradle:gradle docker /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:17-jdk-slim-buster
EXPOSE 6969:6969
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/EmpleadoDepartamentoKtor_4-all.jar /app/empleadoDepartamentoKtor.jar
ENTRYPOINT ["java", "-jar", "/app/empleadoDepartamentoKtor.jar"]