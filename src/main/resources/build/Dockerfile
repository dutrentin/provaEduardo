FROM openjdk:11
MAINTAINER José Marcelo <jose.araujo@asq.com.br>


ARG JAR_FILE=target/*.jar
ADD ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar", "-ospring.profiles.active-docker","/app.jar"]
