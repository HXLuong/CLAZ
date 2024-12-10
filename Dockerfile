# Sử dụng OpenJDK 17 làm base image
FROM openjdk:17-jdk-slim

# Thiết lập thư mục làm việc trong container
WORKDIR /app

# Sao chép file JAR vào container
COPY target/CLAZ-0.0.1-SNAPSHOT.jar app.jar

# Cấu hình cổng container
EXPOSE 8080

# Lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
