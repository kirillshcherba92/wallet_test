FROM eclipse-temurin:21
WORKDIR /app
COPY ./target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar", "--spring.config.location=classpath:./application.properties,file:./application.yml" ]