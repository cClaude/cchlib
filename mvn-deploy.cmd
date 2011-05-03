Call mvn-clean-package-javadoc.cmd
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )


@Echo ON
@Echo ------------------------------------------
@REM Echo mvn deploy -e --projects cchlib-core,cchlib-j2ee
@Echo mvn deploy -e
@Echo (Not done by this batch)
