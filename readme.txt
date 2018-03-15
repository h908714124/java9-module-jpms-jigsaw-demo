#!/bin/bash

set -e

rm -rf target myjre

# Compile module
javac -d target/classes \
      --module-source-path src \
      src/org.modules.b/module-info.java \
      src/org.modules.b/org/modules/b/Main.java

if [[ ${1} == "fast" ]]; then
	java -p target/classes -m org.modules.b/org.modules.b.Main
	exit 0
fi

jlink -p ${JAVA_HOME}/jmods:target/classes \
      --compress 2 \
      --no-header-files \
      --add-modules java.base \
      --output myjre

sudo docker build -t java9-module-jpms-jigsaw-demo .
sudo docker run java9-module-jpms-jigsaw-demo:latest

