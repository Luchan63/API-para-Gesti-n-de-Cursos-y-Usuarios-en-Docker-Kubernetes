FROM openjdk:17-jdk-alpine as builder
ARG MSVC_NAME=msvc-usuario
WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

COPY ./$MSVC_NAME/src ./src

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
ARG MSVC_NAME=msvc-usuario
ARG TARGET_FORDER=/app/msvc-usuario/target
COPY --from=builder $TARGET_FORDER/msvc-usuario-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8001
ENV PORT $PORT_APP


EXPOSE $PORT
CMD ["java", "-jar", "msvc-usuario-0.0.1-SNAPSHOT.jar"]