#
#
# Projeto poc-excecoes-alta-baixa
# Equipe NISA
#
#
FROM openjdk:8-jre-alpine
WORKDIR	/app
COPY target/poc-excecoes-alta-baixa-simulador-baixa-0.0.1.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.datasource.url=jdbc:postgresql://database:5432/postgres", "--spring.rabbitmq.host=rabbitmq"]