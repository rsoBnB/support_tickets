FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./api/target /app

EXPOSE 8083

CMD ["java", "-jar", "reviews-api-1.0.0-SNAPSHOT.jar"]