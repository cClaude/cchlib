REM start C:\Apps\cygwin64\bin\mintty.exe /bin/env CHERE_INVOKING=1 /bin/bash -l 

Set CYGWiN=C:\Apps\cygwin64

set JAVA_HOME=%JAVA_7_HOME%

set PATH=%PATH%;%CYGWIN%\bin
start /B mintty /bin/env CHERE_INVOKING=1 /bin/bash -l

