FROM store/oracle/serverjre:8
COPY build/libs/hrms*.jar /tmp/hrms.jar
COPY tools/sqljdbc_auth.dll /usr/java/jdk1.8.0_181/bin/
CMD [ "java", "-jar", "/tmp/hrms.jar", "--server.servlet.context-path=/hrms", "&" ]
