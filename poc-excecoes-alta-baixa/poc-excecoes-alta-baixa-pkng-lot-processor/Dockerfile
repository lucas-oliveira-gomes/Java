#
#
# Projeto poc-excecoes-alta-baixa
# Equipe NISA
#
#
FROM openjdk:8-jre-alpine
WORKDIR	/app
COPY target/poc-excecoes-alta-baixa-pkng-lot-processor-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.rabbitmq.host=rabbitmq"]