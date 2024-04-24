#!/bin/bash
shopt -s globstar # enable recursive globbing
javac **/*.java
jar cfm MyApp.jar manifest.txt **/*.class
rm **/*.class
# run java -jar MyApp.jar to execute the jar file