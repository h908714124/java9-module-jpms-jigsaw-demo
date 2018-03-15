FROM debian:stable-slim

RUN ["useradd", "-ms", "/bin/bash", "newuser"]

USER newuser
WORKDIR /home/newuser

ADD myjre myjre

CMD myjre/bin/java ${JAVA_OPTS} \
    -m org.modules.b/org.modules.b.Main
