@IF NOT DEFINED JAVA_7_HOME (
  @Echo ERROR JAVA_7_HOME not define
  Pause
  Goto :eof
  )

@IF NOT EXIST "%JAVA_7_HOME%" (
  @Echo ERROR JAVA_7_HOME not found: "%JAVA_7_HOME%"
  Pause
  Goto :eof
  )

@Set JAVA_HOME=%JAVA_7_HOME%

@CALL mvn -Dproject.javadoc.source.version=1.7 -Dproject.javadoc.target.version=1.5 clean compile
@Echo EL=%ERRORLEVEL%
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn clean
  Pause
  Goto :eof
  )

@IF NOT DEFINED JAVA_6_HOME (
  @Echo ERROR JAVA_6_HOME not define
  Pause
  Goto :eof
  )

@IF NOT EXIST "%JAVA_6_HOME%" (
  @Echo ERROR JAVA_6_HOME not found: "%JAVA_6_HOME%"
  Pause
  Goto :eof
  )

@Set JAVA_HOME=%JAVA_6_HOME%

@CALL mvn test
@Echo EL=%ERRORLEVEL%
IF ERRORLEVEL 1 (
  @Echo ERROR in: mvn clean
  Pause
  Goto :eof
  )
