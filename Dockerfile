FROM eclipse-temurin:17-jdk AS build
WORKDIR /src
COPY MiniATM.java .
RUN javac MiniATM.java

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /src/MiniATM.class .
# Console app: run interactively with docker run -it.
ENTRYPOINT ["java", "MiniATM"]
