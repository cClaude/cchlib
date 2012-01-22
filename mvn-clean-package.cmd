@CALL mvn clean
@Echo EL=%ERRORLEVEL%
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn clean
  Pause
  Goto :eof
  )
@CALL mvn package --errors
@Echo EL=%ERRORLEVEL%
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn package -e
  Pause
  Goto :eof
  )
