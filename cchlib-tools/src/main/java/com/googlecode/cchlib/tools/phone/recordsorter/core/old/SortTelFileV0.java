package samples.batchrunner.tel.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import com.googlecode.cchlib.io.FileIterator;
import com.googlecode.cchlib.io.filefilter.DirectoryFileFilter;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 *
 *
 */
public class SortTelFileV0
{
    private File                 sourceFolderFile;
    private DestinationFoldersV0 destinationFolders;

    public SortTelFileV0(File sourceFolderFile, File destinationFolderFile) throws IOException
    {
        this.sourceFolderFile   = sourceFolderFile;
        this.destinationFolders = new DestinationFoldersV0( destinationFolderFile );
        
        log( "SOURCE:" + sourceFolderFile );
        log( "DEST  :" + destinationFolderFile );
    }

    public void sort()
    {
        FileIterator iter = new FileIterator( sourceFolderFile, new DirectoryFileFilter() );

        while( iter.hasNext() ) {
            File   file   = iter.next();
            String number = file.getName();

            File destination = destinationFolders.getFolder( number );

            moveFileTo( file, destination );
        }
    }

    private void moveFileTo( File source, File destinationFolder )
    {
        File target = new File( destinationFolder, source.getName() );

        try {
            log( "move " + source + " to " + target );
            Files.move( source.toPath(), target.toPath(), REPLACE_EXISTING );
            }
        catch( IOException e ) {
            log( "Can not move " + source + " to " + destinationFolder );
            }
    }

    private void log( String msg )
    {
        System.out.println( msg );
    }
    
    public static void main( String[] args ) throws IOException
    {
        File sourceFolderFile   = new File( args[ 0 ] );
        File destinationFolders = new File( args[ 1 ] );
        
        SortTelFileV0 instance = new SortTelFileV0( sourceFolderFile, destinationFolders );

        instance.sort();
    }

}

/*
*****************************************************************

@Echo OFF
CD /D "%~pd0"
FOR /R %%F IN (*.3gp) DO Call .\sortFilesStep1F.cmd "%%~F"

*****************************************************************

@Echo OFF
Set FILE=%~n1
Set EXTNUM=%FILE:~17%
Set IN=%EXTNUM:~-3%
Set OUT=%EXTNUM:~-4%

Echo File=%FILE%
@REM Echo EXTNUM=%EXTNUM%
@REM Echo IN=%IN%
@REM Echo OUT=%OUT%

IF "%IN%"=="_In" GOTO IN
IF "%OUT%"=="_Out" GOTO OUT

@Echo Can't manage [%~1]
GOTO :EOF

:IN
Set NUM=%EXTNUM:~0,-3%
GOTO MOVEFILE

:OUT
Set NUM=%EXTNUM:~0,-4%
GOTO MOVEFILE

:MOVEFILE
Echo NUM=%NUM% - [%NUM~0,3%]

IF "%NUM:~0,3%"=="+33" GOTO FR
IF "%NUM:~0,2%"=="33" GOTO FR2
IF "%NUM:~0,3%"=="+41" GOTO CH
Set NUMOK=%NUM%
GOTO NUMOK

:FR
Set NUMOK=0%NUM:~3%
GOTO NUMOK

:FR2
Set NUMOK=0%NUM:~2%
GOTO NUMOK

:CH
Set NUMOK=00%NUM:~1%
GOTO NUMOK

:NUMOK
Echo NUMOK=%NUMOK%

Set OUTPUTDIR=%~pd0..\_SortStep1_\%NUMOK%
IF NOT EXIST "%OUTPUTDIR%" MKDIR "%OUTPUTDIR%"
@ECHO MOVE "%~1" "%OUTPUTDIR%"
MOVE "%~1" "%OUTPUTDIR%"

*****************************************************************

*/
