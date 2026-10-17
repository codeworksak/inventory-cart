FROM eclipse-temurin:25-jre-alpine

WORKDIR /inventory-cart

ARG JAR_FILES=target/*.jar
COPY ${JAR_FILES} ./inventory-cart.jar

ENV REDIS_HOST=172.17.0.3
ENV REDIS_PORT=6379
ENV RABBIT_HOST=172.17.0.2
ENV RABBIT_PORT=5672
ENV RABBIT_Q="order-queue"
ENV RABBIT_EXCHANGE="order-exchange"
ENV RABBIT_ROUTE_KEY="ordering"
ENV RABBIT_USER=guest
ENV RABBIT_PASS=guest

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "inventory-cart.jar"]