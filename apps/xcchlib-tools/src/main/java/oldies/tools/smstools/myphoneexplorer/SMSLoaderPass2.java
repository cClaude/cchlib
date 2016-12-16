package oldies.tools.smstools.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import org.apache.log4j.Logger;

public class SMSLoaderPass2
{
    private static final Logger LOGGER = Logger.getLogger(SMSLoaderPass2.class);

    private SMSLoaderPass2()
    {
        //All static
    }

    public static void main( final String[] args ) throws IOException, ParseException, InconsistantSMSException
    {
        LOGGER.info( "starting" );

        final String[] inFiles = {
            "outputPass1.csv"
            };
        final String  outFile = "outputPass2.csv";

        run( inFiles, outFile );
    }

    public static void run( final String[] inFiles, final String outFile )
        throws InconsistantSMSException, IOException, ParseException
    {
        final SMSLoader loader = new SMSLoader();

        //
        // Read all files
        //
        for( final String filename : inFiles ) {
            final File file = new File( filename );

            LOGGER.info( "reading: " + file );

            loader.addFile( file );
        }

        loader.removeClones();
        loader.sort();

        loader.save( new File( outFile ) );
    }
}
