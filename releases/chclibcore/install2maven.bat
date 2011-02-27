Set MYVER=03a.34.141

CALL mvn install:install-file -Dfile=CHCLibCore.%MYVER%.jar -DgroupId=com.googlecode.chclib -DartifactId=chclibcore -Dversion=%MYVER% -Dpackaging=jar

Pause
