FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/quartz-retry*.jar quartz-retry.jar
CMD java ${JAVA_OPTS} -jar quartz-retry.jar