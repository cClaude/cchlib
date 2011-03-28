Set MYVER=03a.38.10

CALL mvn install:install-file -Dfile=CHCLibCore.%MYVER%.jar -DgroupId=com.googlecode.chclib -DartifactId=chclibcore -Dversion=%MYVER% -Dpackaging=jar

Pause
