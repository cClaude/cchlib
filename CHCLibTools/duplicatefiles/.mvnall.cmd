@CALL mvn clean package
@Echo EL=%ERRORLEVEL%

@REM %JAVA_HOME%\bin\jar -xvf target\duplicatefiles-1.0-SNAPSHOT.jar META-INF/MANIFEST.MF
@REM
@REM type META-INF\MANIFEST.MF
@REM sleep 1
@REM rd /S/Q META-INF
@REM %JAVA_HOME%\bin\java -jar target\duplicatefiles-1.0-SNAPSHOT.jar

