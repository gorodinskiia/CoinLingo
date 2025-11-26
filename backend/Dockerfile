# Use an official OpenJDK runtime
FROM eclipse-temurin:21-jdk-jammy

# Set environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# Set working directory
WORKDIR /app

# Copy Gradle wrapper and build files first (caching)
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

# Copy backend source
COPY src ./src

# Make Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the application (produces a JAR)
RUN ./gradlew clean build -x test

# Expose port 8080
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "build/libs/backend-0.0.1-SNAPSHOT.jar"]
