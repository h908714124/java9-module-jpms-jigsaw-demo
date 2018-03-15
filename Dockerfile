FROM debian:stable-slim

RUN ["useradd", "-ms", "/bin/bash", "newuser"]

USER newuser
WORKDIR /home/newuser

ADD myjre myjre
ADD target target

CMD ["myjre/bin/java", \
     "-p", "target/classes", \
     "-m", "org.modules.b/org.modules.b.Main"]
