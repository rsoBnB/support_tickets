FROM openjdk:8-jre-alpine

RUN mkdir /app

WORKDIR /app

ADD ./api/target /app

EXPOSE 8087

CMD ["java", "-jar", "support_tickets-api-1.0.0-SNAPSHOT.jar"]