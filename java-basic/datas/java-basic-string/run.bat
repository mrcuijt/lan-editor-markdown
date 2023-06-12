@echo off
javac -classpath ".;./libs/*" Demo.java
java -classpath ".;./libs/*" Demo
echo "This program is finished."