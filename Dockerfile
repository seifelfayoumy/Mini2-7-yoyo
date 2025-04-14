FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

# RUN mvn clean package -DskipTests

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]