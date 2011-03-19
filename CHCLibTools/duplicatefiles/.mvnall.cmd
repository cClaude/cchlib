@CALL mvn clean package
@Echo EL=%ERRORLEVEL%

%JAVA_HOME%\bin\jar -xvf target\duplicatefiles-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF

type META-INF\MANIFEST.MF
sleep 1
rd /S/Q META-INF
%JAVA_HOME%\bin\java -jar target\duplicatefiles-1.0-SNAPSHOT.jar
