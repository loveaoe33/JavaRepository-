# 使用官方的 OpenJDK 作為基礎映像
FROM openjdk:17-jdk-slim

# 設定工作目錄
WORKDIR /app

# 從 target 目錄將 jar 包複製到容器中
COPY target/spring-project-2026-0.0.1-SNAPSHOT.jar main.jar

# 啟動應用程式
ENTRYPOINT ["java", "-jar", "main.jar"]

# 暴露應用程式運行的端口
EXPOSE 8080