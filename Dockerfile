FROM openjdk:17-jdk-slim

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Fix permission
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -B

COPY src ./src

# Build app
RUN ./mvnw clean package -DskipTests

EXPOSE 10000

ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE=production

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar target/airtel-inventory-1.0.0.jar"]