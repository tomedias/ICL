
cd parser
javacc Parser.jj
cd ..
shopt -s globstar # enable recursive globbing
javac **/*.java
rm output/*.j
jar cfm Interpreter.jar manifest_interpreter.txt **/*.class
jar cfm Compiler.jar manifest_compiler.txt **/*.class
rm -r **/*.class

