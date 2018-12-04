FROM java:8
COPY build/libs/hrms*.jar /tmp/hrms.jar
CMD [ "java", "-jar", "/tmp/hrms.jar", "--server.servlet.context-path=/hrms", "&" ]
