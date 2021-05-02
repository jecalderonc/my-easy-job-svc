FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=my-easy-job-svc/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
