package alpha.com.googlecode.cchlib.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import alpha.com.googlecode.cchlib.io.AlternateDataStream;
import junit.framework.TestCase;

public class AlternateDataStreamTest extends TestCase
{
    public static final File TEMP_DIR_FILE = new File( System.getProperty("java.io.tmpdir" ) );

    public void testXX() throws IOException
    {
        File[] rootsFile = File.listRoots();
        
        for( File r : rootsFile ) {
            System.out.printf( "Check: %s\n", r );
            AlternateDataStream ads = new AlternateDataStream(r,"~ThisFileShouldNotExist.tmp","Test");
            
            System.out.printf( "AlternateDataStream supported for %s ? %b\n", r, ads.isSupported());
            
            if( ads.isSupported() ) {
                FileOutputStream fos = new FileOutputStream(ads.getStreamFile());
                fos.write( "test".getBytes() );
                fos.close();
                ads.getStreamSupportFile().delete();
            }
            
        }
    }
}

