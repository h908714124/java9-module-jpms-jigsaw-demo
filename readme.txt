#!/bin/bash

set -e

rm -rf module-a/target module-b/target module-c/target

# Compile module a
javac -d module-a/target/classes module-a/src/org/modules/a/Thing.java \
      module-a/src/org.modules.a/module-info.java

# Compile module c
javac -d module-c/target/classes module-c/src/org/modules/c/Ting.java \
      module-c/src/org.modules.c/module-info.java

# Compile module b
javac --module-path module-a/target/classes:module-c/target/classes \
      -cp module-a/target/classes:module-c/target/classes \
      -d module-b/target/classes module-b/src/org/modules/b/Main.java \
      module-b/src/org.modules.b/module-info.java 

# Run module b
java -cp module-a/target/classes:module-b/target/classes:module-c/target/classes \
     --module-path module-a/target/classes:module-b/target/classes:module-c/target/classes \
     --module org.modules.b/org.modules.b.Main
