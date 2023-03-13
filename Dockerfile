FROM openjdk:17-oracle
MAINTAINER hasan-cerit
COPY target/simplebanking.jar simplebanking.jar
ENTRYPOINT ["sh", "-c", "java -jar /simplebanking.jar ${0} ${@}"]