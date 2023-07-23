FROM openjdk:11-jdk-slim
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-host:3306/myDb
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=enactor
COPY target/*jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]

