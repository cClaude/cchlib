Call mvn clean
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn clean
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Call mvn compile -e
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn compile -e
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
rem Call mvn install package -e
Call mvn package -e
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn package -e
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Call mvn javadoc:javadoc -e
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn javadoc:javadoc -e
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
