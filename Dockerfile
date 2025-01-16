# Usa una imagen base con Java 11
FROM openjdk:11-jdk-slim

# Copia el archivo JAR generado por tu aplicación Spring Boot
COPY target/your-app.jar /your-app.jar

# Expone el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/your-app.jar"]
