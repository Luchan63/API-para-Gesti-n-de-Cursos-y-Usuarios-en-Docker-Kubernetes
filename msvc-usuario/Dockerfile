FROM openjdk:17
WORKDIR /app/msvc-usuario

COPY ./pom.xml /app
COPY ./msvc-usuario/.mvn ./.mvn
COPY ./msvc-usuario/mvnw .
COPY ./msvc-usuario/pom.xml .

COPY ./msvc-usuario/src ./src

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

RUN ./mvnw clean package -DskipTests



EXPOSE 8001
ENTRYPOINT ["java", "-jar", "./target/msvc-usuario-0.0.1-SNAPSHOT.jar"]