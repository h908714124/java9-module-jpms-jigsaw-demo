#!/bin/bash

set -e

# Clean
rm -rf lib module-a/target module-b/target module-c/target myjre
mkdir lib

# Compile module a
javac -d module-a/target/classes \
      $(find module-a/src -name "*.java")

# Compile module c
javac -d module-c/target/classes \
      $(find module-c/src -name "*.java")

jar --create --file=lib/module-a@1.0.jar --module-version=1.0 -C module-a/target/classes .
jar --create --file=lib/module-c@1.0.jar --module-version=1.0 -C module-c/target/classes .

# Compile module b
javac -d module-b/target/classes \
      -p lib \
      $(find module-b/src -name "*.java")

jar --create --file=lib/module-b.jar --main-class=org.modules.b.Main -C module-b/target/classes .

if [[ ${1} == "smoketest" ]]; then
    java -p lib -m org.modules.b
    exit 0
fi

# Create custom JRE
jlink -p ${JAVA_HOME}/jmods:lib \
      --add-modules java.base,org.modules.b \
      --output myjre

# Run module b
myjre/bin/java -m org.modules.b
