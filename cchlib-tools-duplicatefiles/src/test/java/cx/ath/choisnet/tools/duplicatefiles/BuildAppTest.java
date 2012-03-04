package cx.ath.choisnet.tools.duplicatefiles;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.IOHelper;
import com.googlecode.cchlib.io.FileHelper;

/**
 * Build test folder
 */
public class BuildAppTest
{
    private final static Logger logger = Logger.getLogger( BuildAppTest.class );

    //@Test
    public void test_BuidTst() throws IOException
    {
        final File tstRootFolder = new File(
                    FileHelper.getTmpDirFile(),
                    ".duplicatefiles-tst"
                    );
        final String[] strings = new String[ 5000 ];

        logger.info( "BuildAppTest in: " + tstRootFolder );

        {
            final File refDir = new File( tstRootFolder, "REF" );

            refDir.mkdirs();

            for( int i = 0; i<strings.length; i++ ) {
                strings[ i ] = Integer.toHexString( i );

                File file = new File( refDir, "file-" + i );
                IOHelper.toFile( file, strings[ i ] );
                }

            logger.info( "BuildAppTest ref done" );
        }

        final File toDelDir = new File( tstRootFolder, "DEL" );

        for( int d = 0; d<5; d++ ) {
            final File tstFolder = new File(
                    toDelDir,
                    "dir-" + d
                    );

            tstFolder.mkdirs();

            for( int i = 0; i<strings.length; i++ ) {
                File file = new File( tstFolder, "file-" + i );

                IOHelper.toFile( file, strings[ i ] );
                }

            logger.info( "BuildAppTest pass " + d + " done" );
           }

        logger.info( "BuildAppTest done" );
    }

    public static void main(String[] args) throws IOException
    {
        new BuildAppTest().test_BuidTst();
    }
}
