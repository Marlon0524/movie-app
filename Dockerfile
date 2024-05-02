FROM openjdk:17
VOLUME /tmp
EXPOSE 8020
ADD ./target/movie-0.0.1-SNAPSHOT.jar movie-app.jar
ENTRYPOINT ["java", "-jar", "movie-app.jar"]