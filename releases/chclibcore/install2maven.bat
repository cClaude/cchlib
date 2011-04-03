Set MYVER=4.1.0-SNAPSHOT

CALL mvn install:install-file -Dfile=chclibcore-%MYVER%.jar -DgroupId=com.googlecode.chclib -DartifactId=chclibcore -Dversion=%MYVER% -Dpackaging=jar

Pause
