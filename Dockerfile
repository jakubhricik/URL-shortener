FROM maven:3-openjdk-17-slim as build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:17-jdk-slim
COPY --from=build /usr/src/app/target/*.jar /usr/app/URL-shortener.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/URL-shortener.jar"]
