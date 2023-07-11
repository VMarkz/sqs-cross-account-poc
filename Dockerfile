FROM openjdk:17-jdk-alpine
# ENV AWS_ACCESS_KEY_ID=none
# ENV AWS_SECRET_ACCESS_KEY=none
# ENV AWS_SESSION_TOKEN=none
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]