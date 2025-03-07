# Estágio de compilação
FROM maven:3.9.6 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests -e

# Estágio de construção da imagem
FROM openjdk:21
WORKDIR /app

# Define o argumento para o perfil ativo (valor padrão é "dev")
ARG SPRING_PROFILES_ACTIVE=dev
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# Copia o JAR gerado no estágio anterior
COPY --from=builder /app/target/CluBee-Vantagens-Auth-Microservice-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

# Define o comando de execução, passando o perfil ativo como argumento
CMD ["java", "-jar", "CluBee-Vantagens-Auth-Microservice-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}", "--spring.rabbitmq.addresses=amqps://drxyafjf:kANMRmSVr3eY370hZpt4iQ4x7zDxA3nJ@jackal.rmq.cloudamqp.com:5671/drxyafjf", "--spring.rabbitmq.ssl.enabled=true"]
