FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn ./.mvn
COPY pom.xml .
RUN ./mvnw wrapper:wrapper -DmavenWrapperVersion=0.5.5
COPY src ./src
RUN ./mvnw clean install -DskipTests
EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]