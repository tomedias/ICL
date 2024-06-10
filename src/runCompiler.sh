
mkdir -p output/compiled
java -jar Compiler.jar
cd output/compiled
java -jar ../jasmin.jar ../*.j
java Demo
#rm ../*.j # Remove this to see .j files