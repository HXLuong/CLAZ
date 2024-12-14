# Sử dụng OpenJDK 17 làm base image
FROM openjdk:17-jdk-slim

# Sao chép file JAR vào container
COPY target/CLAZ-0.0.1-SNAPSHOT.jar CLAZ-0.0.1-SNAPSHOT.jar
#
EXPOSE 8080
# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/CLAZ-0.0.1-SNAPSHOT.jar"]
