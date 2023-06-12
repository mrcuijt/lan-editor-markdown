@echo off
javac -classpath ".;" ./examples/set2/*.java
java  -classpath ".;" examples.set2.B
echo "This program is finished."