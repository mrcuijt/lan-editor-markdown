@echo off
javac -encoding utf8 -classpath ".;./lib/*" Demo.java
java -Dfile.encoding=utf-8 -classpath ".;./lib/*" Demo
echo 'This program is finished.'
