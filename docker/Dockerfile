FROM nexusrepository.bradesco.com.br:8500/ubi8/openjdk-17-runtime:1.14-4.1665493383 as stage

WORKDIR /home/jboss/application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} bff-kit-springboot.jar
RUN java -Djarmode=layertools -jar bff-kit-springboot.jar extract

FROM nexusrepository.bradesco.com.br:8500/ubi8/openjdk-17-runtime:1.14-4.1665493383

EXPOSE 8080

COPY --from=stage /home/jboss/application/dependencies/ ./
COPY --from=stage /home/jboss/application/spring-boot-loader/ ./
COPY --from=stage /home/jboss/application/snapshot-dependencies/ ./
COPY --from=stage /home/jboss/application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
