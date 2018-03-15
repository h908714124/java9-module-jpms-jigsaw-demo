#!/bin/bash
#
# Running a Java 9 custom JRE inside a Docker container.
#
# Build docker image: bash readme.txt
# Quick test:         bash readme.txt smoketest
# Build and run:      bash readme.txt run
# JVM settings:       JAVA_OPTS="-Xmx64m" bash readme.txt run
#

set -e

# Clean
rm -rf target myjre

# The directory structure in ./src is described here:
# http://openjdk.java.net/projects/jigsaw/quick-start
javac -d target/classes \
      --module-source-path src \
      src/org.modules.b/module-info.java \
      src/org.modules.b/org/modules/b/Main.java

if [[ ${1} == "smoketest" ]]; then
    java -p target/classes -m org.modules.b/org.modules.b.Main
    exit 0
fi

# Create self-contained JRE in ./myjre
jlink -p ${JAVA_HOME}/jmods:target/classes \
      --compress 2 \
      --no-header-files \
      --add-modules java.base,org.modules.b \
      --output myjre

# Create docker image (see Dockerfile)
sudo docker build -t java9-module-jpms-jigsaw-demo .

if [[ ${1} == "run" ]]; then
    if [[ -z "${JAVA_OPTS}" ]]; then
        JAVA_OPTS="-Xmx128m"
    fi
    sudo docker run --rm \
        -e JAVA_OPTS="${JAVA_OPTS}" \
        java9-module-jpms-jigsaw-demo:latest
fi
