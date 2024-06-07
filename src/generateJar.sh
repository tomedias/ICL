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

