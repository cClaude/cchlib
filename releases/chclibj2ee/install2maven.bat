Set MYVER=03a.11.0

CALL mvn install:install-file -Dfile=chclibj2ee-%MYVER%.jar -DgroupId=com.googlecode.chclib -DartifactId=chclibj2ee -Dversion=%MYVER% -Dpackaging=jar

Pause
