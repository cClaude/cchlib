Call mvn-clean-package-javadoc.cmd
IF ERRORLEVEL 1 (
  Pause
  Goto :eof
  )

@Echo ON
Rd /S/Q .\releases\cchlib-core\apidocs
Rd /S/Q .\releases\cchlib-j2ee\apidocs
Rd /S/Q .\releases\cchlib-sys\apidocs

Del /Q .\releases\cchlib-core\cchlib-core-*.jar
Del /Q .\releases\cchlib-j2ee\cchlib-j2ee-*.jar
Del /Q .\releases\cchlib-sys\cchlib-sys-*.jar
Del /Q .\releases\cchlib-tools\cchlib-tools-*.jar

Copy .\cchlib-core\target\cchlib-core-*.jar   .\releases\cchlib-core\
Copy .\cchlib-j2ee\target\cchlib-j2ee-*.jar   .\releases\cchlib-j2ee\
Copy .\cchlib-sys\target\cchlib-sys-*.jar     .\releases\cchlib-sys\
Copy .\cchlib-tools\target\cchlib-tools-*.jar .\releases\cchlib-tools\

Copy .\cchlib-tools-duplicatefiles\target\cchlib-tools-duplicatefiles-*.jar           .\releases\cchlib-tools\
Copy .\cchlib-tools-editressourcebundle\target\cchlib-tools-editressourcebundle-*.jar .\releases\cchlib-tools\

Md .\releases\cchlib-core\apidocs
Md .\releases\cchlib-j2ee\apidocs
Md .\releases\cchlib-sys\apidocs

XCopy .\cchlib-core\target\site\apidocs .\releases\cchlib-core\apidocs\ /E /Y /Q
XCopy .\cchlib-j2ee\target\site\apidocs .\releases\cchlib-j2ee\apidocs\ /E /Y /Q
XCopy .\cchlib-sys\target\site\apidocs  .\releases\cchlib-sys\apidocs\ /E /Y /Q

@Rem not javadoc for cchlib-tools
Del /Q .\releases\cchlib-tools\cchlib-tools-*-javadoc.jar

@Echo ON
@Echo ------------------------------------------
@REM Echo mvn deploy -e --projects cchlib-core,cchlib-j2ee
@Echo mvn deploy -e
@Echo (Not done by this batch)
