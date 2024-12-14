FROM openjdk:17-jdk-alpine3.14

COPY target/CLAZ-0.0.1-SNAPSHOT.jar CLAZ-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/CLAZ-0.0.1-SNAPSHOT.jar"]
