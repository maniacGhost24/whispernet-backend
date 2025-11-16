###### BUILD #####

FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only pom.xml first to leverage Docker cache
COPY pom.xml .

# Download all dependencies (cached unless pom.xml changes)
RUN mvn -q -e -B dependency:go-offline

# Now copy the entire project
COPY . .

# Build the application (skip tests for faster build)
RUN mvn -q -e -B package -DskipTests

##### RUNTIME #####

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Use port 8080 inside the container
EXPOSE 8080

# Production JVM settings (optimized for containers)
ENV JAVA_OPTS="-XX:+UseContainerSupport \
               -XX:MaxRAMPercentage=75 \
               -XX:+UseG1GC"

# Start the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]