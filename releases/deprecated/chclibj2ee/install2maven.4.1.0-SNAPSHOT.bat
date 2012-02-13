Set GROUPID=com.googlecode.chclib
Set ARTIFACTID=chclibj2ee
Set VERSION=4.1.0-SNAPSHOT

CALL mvn install:install-file -Dfile=chclibj2ee-%VERSION%.jar -DgroupId=%GROUPID% -DartifactId=%ARTIFACTID% -Dversion=%VERSION% -Dpackaging=jar

Pause
