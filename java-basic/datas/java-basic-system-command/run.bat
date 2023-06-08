@echo off
chcp
javac -classpath "./;D:/software/putty/putty/_download;D:/software/putty/putty/_download/lib/*" -encoding utf8 Demo.java
java -classpath "./;D:/software/putty/putty/_download;D:/software/putty/putty/_download/lib/*" -Dfile.encoding=UTF-8 Demo
::java -Dfile.encoding=UTF-8 Demo
echo 'This program is finished.'
