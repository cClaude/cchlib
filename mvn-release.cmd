Call mvn-all.cmd
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )

Rd /S/Q .\releases\chclibcore\api
Rd /S/Q .\releases\chclibj2ee\api

Copy .\chclib-core\target\chclibcore-*.jar .\releases\chclibcore\
Copy .\chclib-j2ee\target\chclibj2ee-*.jar .\releases\chclibj2ee\

Md .\releases\chclibcore\api
XCopy .\chclib-core\target\site\apidocs .\releases\chclibcore\api\ /E /Y /Q

Md .\releases\chclibj2ee\api
XCopy .\chclib-j2ee\target\site\apidocs .\releases\chclibj2ee\api\ /E /Y /Q
