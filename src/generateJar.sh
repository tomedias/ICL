#!/bin/bash
cd parser
javacc Parser.jj
cd ..
shopt -s globstar # enable recursive globbing
javac **/*.java
rm out/*.j
jar cfm Console.jar manifest.txt **/*.class
jar cfm Interpreter.jar manifest_interpreter.txt **/*.class
jar cfm Typechecker.jar manifest_typechecker.txt **/*.class
jar cfm Compiler.jar manifest_compiler.txt **/*.class

echo "Compiled"
java -jar Compiler.jar
rm **/*.class
cd output
java -jar jasmin.jar out.j *.j
java Demo
cd ..
