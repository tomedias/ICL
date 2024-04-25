#!/bin/bash
cd parser
javacc Parser.jj
cd ..
shopt -s globstar # enable recursive globbing
javac **/*.java
jar cfm Console.jar manifest.txt **/*.class
jar cfm Interpreter.jar manifest_interpreter.txt **/*.class
jar cfm Typechecker.jar manifest_typechecker.txt **/*.class
rm **/*.class
# run java -jar MyApp.jar to execute the jar file