FROM openjdk:17-jdk
ARG JAR_FILE=build/libs/chat-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/app.jar"]
