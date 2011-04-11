Call mvn clean
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Call mvn compile -e
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Call mvn install package javadoc:javadoc -e
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )
