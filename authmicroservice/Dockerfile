# docker build -t clubee/backend:1.0  .
# mvn package -DskipTests

# Estágio de compilação
FROM maven:3.9.6 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests

# Estágio de construção da imagem
FROM openjdk:21
WORKDIR /app
COPY --from=builder /app/target/CluBee-Vantagens-Auth-Microservice-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "CluBee-Vantagens-Auth-Microservice-0.0.1-SNAPSHOT.jar"]