@echo off
javac -classpath ".;" ./examples/set2/*.java
java  -classpath ".;" examples.set2.D
echo "This program is finished."