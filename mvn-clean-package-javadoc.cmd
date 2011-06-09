Set EXEC=mvn clean
Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Set EXEC=mvn compile -e --fail-fast
Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
@Rem install ??? TODO fix this if possible
@Set EXEC=mvn install package -e
@Rem Set EXEC=mvn package -e
Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Set EXEC=mvn javadoc:jar -e -Dmaven.test.skip=true
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
Rd /S/Q .\releases\cchlib-core\apidocs
Rd /S/Q .\releases\cchlib-j2ee\apidocs
Rd /S/Q .\releases\cchlib-jdbf\apidocs
Rd /S/Q .\releases\cchlib-sys\apidocs

Del /Q .\releases\cchlib-core\cchlib-core-*.jar
Del /Q .\releases\cchlib-j2ee\cchlib-j2ee-*.jar
Del /Q .\releases\cchlib-jdbf\cchlib-jdbf-*.jar
Del /Q .\releases\cchlib-sys\cchlib-sys-*.jar
Del /Q .\releases\cchlib-tools\cchlib-tools-*.jar

Copy .\cchlib-core\target\cchlib-core-*.jar   .\releases\cchlib-core\
Copy .\cchlib-j2ee\target\cchlib-j2ee-*.jar   .\releases\cchlib-j2ee\
Copy .\cchlib-jdbf\target\cchlib-jdbf-*.jar   .\releases\cchlib-jdbf\
Copy .\cchlib-sys\target\cchlib-sys-*.jar     .\releases\cchlib-sys\
Copy .\cchlib-tools\target\cchlib-tools-*.jar .\releases\cchlib-tools\

Copy .\cchlib-tools-duplicatefiles\target\cchlib-tools-duplicatefiles-*.jar           .\releases\cchlib-tools\
Copy .\cchlib-tools-editressourcebundle\target\cchlib-tools-editressourcebundle-*.jar .\releases\cchlib-tools\

Md .\releases\cchlib-core\apidocs
Md .\releases\cchlib-j2ee\apidocs
Md .\releases\cchlib-jdbf\apidocs
Md .\releases\cchlib-sys\apidocs

XCopy .\cchlib-core\target\apidocs .\releases\cchlib-core\apidocs\ /E /Y /Q
XCopy .\cchlib-j2ee\target\apidocs .\releases\cchlib-j2ee\apidocs\ /E /Y /Q
XCopy .\cchlib-jdbf\target\apidocs .\releases\cchlib-jdbf\apidocs\ /E /Y /Q
XCopy .\cchlib-sys\target\apidocs  .\releases\cchlib-sys\apidocs\ /E /Y /Q

@Rem not javadoc for cchlib-tools
Del /Q .\releases\cchlib-tools\cchlib-tools-*-javadoc.jar
