#!/bin/bash
#
# Create a runnable docker image with Java 9
#
# Build docker image: bash readme.txt
# Quick test:         bash readme.txt smoketest
# Build and run:      bash readme.txt run
#

set -e

# Clean
rm -rf target myjre

# The "strange directory layout" is described here:
# http://openjdk.java.net/projects/jigsaw/quick-start
javac -d target/classes \
      --module-source-path src \
      src/org.modules.b/module-info.java \
      src/org.modules.b/org/modules/b/Main.java

if [[ ${1} == "smoketest" ]]; then
    java -p target/classes -m org.modules.b/org.modules.b.Main
    exit 0
fi

# Create self-contained JRE
jlink -p ${JAVA_HOME}/jmods:target/classes \
      --compress 2 \
      --no-header-files \
      --add-modules java.base \
      --output myjre

# Create a docker image (see Dockerfile)
sudo docker build -t java9-module-jpms-jigsaw-demo .

# Run docker image
if [[ ${1} == "run" ]]; then
    sudo docker run java9-module-jpms-jigsaw-demo:latest
fi
