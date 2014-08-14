#!/bin/bash
#
JAVA="java"
JAR='./DuplicateFilesManager-*.jar'
CMD="${JAVA} -jar ${JAR}"
${CMD}

# activate this line to have stacktrace
# read -p "Press [Enter] key to quit..."
