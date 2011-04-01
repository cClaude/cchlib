@Echo OFF
@Rem
@Rem -----------------------------------------------------------------------
@Rem Nom           : .build.cmd
@Rem Description   :
@Rem Encodage      : DOS
@Rem
@Rem 1.00 2010.08.20  CC - Version initiale
@Rem -----------------------------------------------------------------------
@Rem
@Rem
@Rem
@Call "%~pd0.setVars.cmd"

@Rem If NOT DEFINED CATALINA_HOME (
@Rem   @Echo.
@Rem   @Echo *** La variable CATALINA_HOME n'est pas définie
@Rem   @Echo.
@Rem
@Rem   Pause
@Rem   goto :eof
@Rem   )

If NOT DEFINED ANT_HOME (
  @Echo.
  @Echo *** La variable ANT_HOME n'est pas définie
  @Echo.

  Pause
  goto :eof
  )

If NOT DEFINED JAVA_HOME (
  @Echo.
  @Echo *** La variable JAVA_HOME n'est pas définie
  @Echo.

  Pause
  goto :eof
  )

Cd /D "%~spd0"
Call "%ANT_HOME%\bin\ant" %*
@Echo ERRORLEVEL=%ERRORLEVEL%

:ERROR
Pause

