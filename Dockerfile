FROM maven AS build
COPY pom.xml /CoffeeSpringBoot/pom.xml
WORKDIR /CoffeeSpringBoot
RUN mvn clean install
COPY . .
#ADD target/Yandex-1.0-SNAPSHOT.jar Yandex-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "target/CoffeeSpringBoot-1.0-SNAPSHOT.jar"]

