FROM openjdk:17-slim

WORKDIR /app

COPY . /app/RMS_back/


WORKDIR /app/RMS_back
RUN chmod +x ./gradlew

RUN ./gradlew clean

RUN ./gradlew build

WORKDIR /app/RMS_back/build/libs

CMD ["java", "-jar", "Lime-RMS-0.0.1-SNAPSHOT.jar"]


