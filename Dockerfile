FROM gradle:jdk24-graal as gradle

COPY ./ ./

RUN gradle installDist

FROM openjdk:24-slim

WORKDIR /znatokiBot

COPY --from=gradle /home/gradle/build/install/metarBot/ ./

RUN apt-get update && apt-get install -y curl && apt-get clean && rm -rf /var/lib/apt/lists/*
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 \
  CMD curl --fail http://localhost:81/health || exit 1

CMD ["bin/metarBot"]
