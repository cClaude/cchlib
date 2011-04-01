@Echo OFF
@Rem
@Rem -----------------------------------------------------------------------
@Rem Nom           : .build-prepare.cmd
@Rem Description   :
@Rem Encodage      : DOS
@Rem
@Rem 1.00 2010.08.19  CC - Version initiale
@Rem -----------------------------------------------------------------------
@Rem
@Rem
@Rem
Set LIBDIR=%~pd0libs

@Echo ON
@Rem Copy "C:\Apps\eclipse\plugins\org.eclipse.swt.win32.win32.x86_3.6.0.v3650b.jar" "%LIBDIR%"
@Rem Echo ERRORLEVEL=%ERRORLEVEL%

Copy "C:\Documents and Settings\Claude\workspace\.metadata\.plugins\org.dyno.visual.swing\layoutext\grouplayout.jar" "%LIBDIR%"
@Echo ERRORLEVEL=%ERRORLEVEL%

Pause

