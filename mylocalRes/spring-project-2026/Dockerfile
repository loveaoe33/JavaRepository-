# 使用一个基础镜像
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 将构建好的Jar文件复制到工作目录
COPY target/my-spring-boot-app.jar app.jar

# 运行Jar文件
ENTRYPOINT ["java", "-jar", "app.jar"]
