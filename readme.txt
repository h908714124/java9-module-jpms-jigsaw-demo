#!/bin/bash

set -e

rm -rf module-a/target module-b/target module-c/target myjre

# Compile module a
javac -d module-a/target/classes \
      module-a/src/module-info.java \
      module-a/src/org/modules/a/Thing.java

# Compile module c
javac -d module-c/target/classes \
      module-c/src/module-info.java \
      module-c/src/org/modules/c/Ting.java

# Compile module b
javac -d module-b/target/classes \
      -p module-a/target/classes:module-c/target/classes \
      module-b/src/org/modules/b/Main.java \
      module-b/src/module-info.java

jlink -p ${JAVA_HOME}/jmods:module-a/target/classes:module-b/target/classes:module-c/target/classes \
      --add-modules java.base \
      --output myjre

# Run module b
myjre/bin/java -p module-a/target/classes:module-b/target/classes:module-c/target/classes \
     -m org.modules.b/org.modules.b.Main
