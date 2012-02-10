Set EXEC=mvn clean --offline
REM ******* Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Set EXEC=mvn compile --errors --fail-fast -Dmaven.test.skip=true
REM ******* Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
@Rem install ??? TODO fix this if possible
@Set EXEC=mvn install package --errors --fail-fast
REM *******
@Set EXEC=mvn install package --errors --fail-fast -Dmaven.test.skip=true
Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Set EXEC=mvn javadoc:jar --errors -Dmaven.test.skip=true
Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@REM
@REM javadoc:jar
@REM    creates an archive file of the generated Javadocs. It
@REM    is used during the release process to create the Javadoc
@REM    artifact for the project's release. This artifact is
@REM    uploaded to the remote repository along with the project's
@REM    compiled binary and source archive.
@REM
@REM javadoc:test-jar
@REM    creates an archive file of the generated Test Javadocs.
@REM
@REM javadoc:aggregate-jar
@REM    creates an archive file of the generated Javadocs for an
@REM    aggregator project.
@REM
@REM javadoc:fix
@REM    is an interactive goal which fixes the Javadoc documentation
@REM    and tags for the Java files.
@REM
@Echo ON
Set MODULE_WITH_DOC=cchlib-core;cchlib-j2ee;cchlib-jdbf;cchlib-sys
Set MODULE_WITH_SUBPROJECT=cchlib-core;cchlib-j2ee;cchlib-tools;cchlib-apps

@Echo # Clean previous documentation
For /D %%F IN ( %MODULE_WITH_DOC% ) DO Rd /S/Q ".\releases\%%F\apidocs"

@Echo # Clean previous jar files
For /D %%F IN ( "cchlib-*" ) DO Del /Q ".\releases\%%F\%%F-*.jar"

@Echo # Create directories
For /D %%F IN ( "cchlib-*" ) DO IF NOT EXIST ".\releases\%%F" Md ".\releases\%%F"

@Echo Copy new jar files
For /D %%F IN ( "cchlib-*" ) DO Copy ".\%%F\target\%%F-*.jar" ".\releases\%%F\"

@Echo # Move content of subproject into project
IF NOT EXIST ".\releases\cchlib-apps" Md ."\releases\cchlib-apps"
For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO Echo ************* %%F -- %%P ***************
For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO Del ".\releases\%%F\*-javadoc.jar"
For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO Move ".\releases\%%F\*.jar .\releases\%%P\"
For /D %%P IN ( %MODULE_WITH_SUBPROJECT% ) DO For /D %%F IN ( "%%P-*" ) DO RmDir ".\releases\%%F"

@Echo # Prepare directories for new documentations
For /D %%F IN ( %MODULE_WITH_DOC% ) DO IF NOT EXIST ".\releases\%%F\apidocs" Md ".\releases\%%F\apidocs"

@Echo # Copy new documentations
For /D %%F IN ( %MODULE_WITH_DOC% ) DO XCopy ".\%%F\target\apidocs" ".\releases\%%F\apidocs\" /E /Y /Q

@Echo # Remove javadoc for cchlib-tools {no doc here}
Del /Q ".\releases\cchlib-tools\cchlib-tools-*-javadoc.jar"

@Echo # Remove javadoc for cchlib-apps {no doc here}
Del /Q ".\releases\cchlib-tools\cchlib-apps-*-javadoc.jar"

@Echo # Remove shaded jar file in tools {fix}
Del /Q ".\releases\cchlib-tools\cchlib-tools-*-shaded.jar"
