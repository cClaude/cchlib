Set GROUPID=com.googlecode.chclib
Set ARTIFACTID=chclibcore
Set VERSION=4.1.0-SNAPSHOT

CALL mvn install:install-file -Dfile=chclibcore-%VERSION%.jar -DgroupId=%GROUPID% -DartifactId=%ARTIFACTID% -Dversion=%VERSION% -Dpackaging=jar

rem Set MYVER=4.1.0-SNAPSHOT
rem
rem CALL mvn install:install-file -Dfile=chclibcore-%MYVER%.jar -DgroupId=com.googlecode.chclib -DartifactId=chclibcore -Dversion=%MYVER% -Dpackaging=jar
rem
Pause
