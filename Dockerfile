
# BUILD STAGE
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean bootJar -x test

RUN find build/libs -name "*.jar" ! -name "*-plain.jar" -exec cp {} /app/app.jar \;

# RUN STAGE
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/app.jar app.jar

EXPOSE 8887

ENTRYPOINT ["java", "-jar", "app.jar"]
