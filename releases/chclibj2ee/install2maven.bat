Set MYVER=4.1.0-SNAPSHOT.jar

CALL mvn install:install-file -Dfile=chclibj2ee-%MYVER%.jar -DgroupId=com.googlecode.chclib -DartifactId=chclibj2ee -Dversion=%MYVER% -Dpackaging=jar

Pause
