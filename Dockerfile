FROM gradle:7.5 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean build
FROM adoptopenjdk/openjdk11:jre-11.0.9_11.1-alpine
COPY --from=build /home/gradle/src/build/libs/AppFooBar-1.0.0.jar /app/app-foobar.jar
ENTRYPOINT ["java","-jar","/app/app-foobar.jar", "--spring.profiles.active=prod"]

