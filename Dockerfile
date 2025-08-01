# Usar uma imagem base oficial do OpenJDK para Java 17
FROM openjdk:17-jdk-slim

# Definir o diretório de trabalho dentro do container
WORKDIR /app

# Copiar o wrapper Maven e o pom.xml para o container
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Baixar as dependências Maven para que não sejam rebaixadas toda vez que o código-fonte muda
RUN ./mvnw dependency:go-offline -B

# Copiar o código-fonte da sua aplicação para o container
COPY src ./src

# Construir a aplicação Spring Boot
RUN ./mvnw clean install -DskipTests

# Expor a porta que a aplicação Spring Boot usa
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]