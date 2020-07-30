#Image
FROM openjdk:8-jdk-alpine
ADD target/notreSnapshot.jar app.jar
# Run the Spring boot application
ENTRYPOINT ["java", "-jar", "app.jar"]