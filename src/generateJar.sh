#!/bin/bash
cd parser
javacc Parser.jj
cd ..
rm -r main/*.j
shopt -s globstar # enable recursive globbing
javac **/*.java
jar cfm Console.jar manifest.txt **/*.class
jar cfm Interpreter.jar manifest_interpreter.txt **/*.class
jar cfm Typechecker.jar manifest_typechecker.txt **/*.class
jar cfm Compiler.jar manifest_compiler.txt **/*.class
rm **/*.class
java -jar Compiler.jar
cd main
java -jar jasmin.jar out frame_*.j
java Demo
cd ..
 run java -jar Console.jar to execute the jar file