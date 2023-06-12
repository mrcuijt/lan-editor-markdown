@echo off
set address=%1
javac M.java
java M %address%
echo "This program is finished."
