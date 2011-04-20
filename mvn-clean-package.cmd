@CALL mvn clean
@Echo EL=%ERRORLEVEL%
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )
@CALL mvn package -e
@Echo EL=%ERRORLEVEL%

