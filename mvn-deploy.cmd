Call mvn-clean-package-javadoc.cmd
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )

rem Rd /S/Q .\releases\chclibcore\api
rem Rd /S/Q .\releases\chclibj2ee\api
rem
rem Copy .\chclib-core\target\chclibcore-*.jar .\releases\chclibcore\
rem Copy .\chclib-j2ee\target\chclibj2ee-*.jar .\releases\chclibj2ee\
rem
rem Md .\releases\chclibcore\api
rem XCopy .\chclib-core\target\site\apidocs .\releases\chclibcore\api\ /E /Y /Q
rem
rem Md .\releases\chclibj2ee\api
rem XCopy .\chclib-j2ee\target\site\apidocs .\releases\chclibj2ee\api\ /E /Y /Q
rem

@Echo ON
@Echo ------------------------------------------
@Echo mvn deploy -e --projects cchlib-core,cchlib-j2ee
@Echo (Not done by this batch)
