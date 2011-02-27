Set MYVER=03a.09.36

CALL mvn install:install-file -Dfile=CHCLibJ2EE.%MYVER%.jar -DgroupId=com.googlecode.chclib -DartifactId=chclibj2ee -Dversion=%MYVER% -Dpackaging=jar

Pause
