@echo off
javac -classpath ".;" ./examples/set1/*.java
java  -classpath ".;" examples.set1.C
echo "This program is finished."