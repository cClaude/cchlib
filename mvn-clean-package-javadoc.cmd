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
@Rem Set EXEC=mvn install package -e
Set EXEC=mvn package -e -Dmaven.test.skip=true
Call %EXEC%
IF ERRORLEVEL 1 (
  @Echo ERROR in: %EXEC%
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Set EXEC=mvn javadoc:jar -e
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
