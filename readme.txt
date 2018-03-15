#!/bin/bash

set -e

rm -rf module-a/target module-b/target module-c/target myjre

# Compile module b
javac -d module-b/target/classes \
      --module-source-path module-a/src:module-c/src:module-b/src \
      module-b/src/org.modules.b/org/modules/b/Main.java \
      module-b/src/org.modules.b/module-info.java 

jlink -p ${JAVA_HOME}/jmods:module-a/target/classes:module-b/target/classes:module-c/target/classes \
      --add-modules java.base \
      --output myjre

# Run module b
myjre/bin/java -p module-a/target/classes:module-b/target/classes:module-c/target/classes \
     -m org.modules.b/org.modules.b.Main
