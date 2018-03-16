#!/bin/bash

set -e

# Clean
rm -rf lib module-a/target module-b/target module-c/target myjre
mkdir lib

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

# Create jars
jar --create --file=lib/module-a@1.0.jar --module-version=1.0 -C module-a/target/classes .
jar --create --file=lib/module-c@1.0.jar --module-version=1.0 -C module-c/target/classes .
jar --create --file=lib/module-b.jar --main-class=org.modules.b.Main -C module-b/target/classes .

# Create custom JRE
jlink -p ${JAVA_HOME}/jmods:lib/module-a@1.0.jar:lib/module-c@1.0.jar:lib/module-b.jar \
      --add-modules java.base,org.modules.b \
      --output myjre

# Run module b
myjre/bin/java -m org.modules.b
