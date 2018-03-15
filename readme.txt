#!/bin/bash

set -e

rm -rf target myjre

# Compile module
javac -d target/classes \
      --module-source-path src \
      src/org.modules.b/module-info.java \
      src/org.modules.b/org/modules/b/Main.java

jlink -p ${JAVA_HOME}/jmods:target/classes \
      --compress 2 \
      --no-header-files \
      --add-modules java.base \
      --output myjre

# Run module b
myjre/bin/java -p target/classes \
	       -m org.modules.b/org.modules.b.Main
