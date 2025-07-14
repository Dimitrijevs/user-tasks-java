FROM openjdk:22-jdk

ADD target/task-app-1.0.jar task-app-1.0.jar

ENTRYPOINT [ "java", "-jar", "/task-app-1.0.jar" ]