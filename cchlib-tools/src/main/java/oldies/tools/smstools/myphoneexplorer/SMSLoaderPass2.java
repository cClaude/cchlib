package oldies.tools.smstools.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import org.apache.log4j.Logger;

/**
 *
 */
public class SMSLoaderPass2 
{
    private static final Logger slogger = Logger.getLogger(SMSLoaderPass2.class);

    private SMSLoaderPass2()
    {
        //All static
    }

    /**
     * @param args
     * @throws IOException 
     * @throws ParseException 
     * @throws InconsistantSMSException 
     */
    public static void main( String[] args ) throws IOException, ParseException, InconsistantSMSException
    {
        slogger.info( "starting" );

        String[] inFiles = {
            "outputPass1.csv"
            };
        String  outFile = "outputPass2.csv";

        SMSLoader loader = new SMSLoader();

        //
        // Read all files
        //
        for( String fn : inFiles ) {
            File            f = new File( fn );

            slogger.info( "reading: " + f );

            loader.addFile( f );
        }

        loader.removeClones();
        loader.sort();

        loader.save( new File( outFile ) );
    }
}
