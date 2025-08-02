FROM gradle:jdk24-graal as gradle

COPY ./ ./

RUN gradle installDist

FROM openjdk:24-slim

WORKDIR /znatokiBot

COPY --from=gradle /home/gradle/build/install/metarBot/ ./

CMD ["bin/metarBot"]
