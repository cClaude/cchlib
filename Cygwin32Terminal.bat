@REM start C:\Apps\cygwin32\bin\mintty.exe /bin/env CHERE_INVOKING=1 /bin/bash -l

set CYGWIN=C:\Apps\cygwin32
set JAVA_HOME=%JAVA_8_HOME%

set PATH=%PATH%;%CYGWIN%\bin
start /B mintty /bin/env CHERE_INVOKING=1 /bin/bash -l
