Call mvn clean
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Call mvn compile
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )

@Echo ON
@Echo ------------------------------------------
Call mvn install package javadoc:javadoc
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )
