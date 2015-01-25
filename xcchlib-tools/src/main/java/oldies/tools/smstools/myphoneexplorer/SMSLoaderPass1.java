package oldies.tools.smstools.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import oldies.tools.smstools.myphoneexplorer.thunderbird.MboxFile;
import org.apache.log4j.Logger;


/**
 *
 */
public class SMSLoaderPass1
{
    private static final Logger LOGGER = Logger.getLogger(SMSLoaderPass1.class);

    private SMSLoaderPass1()
    {
        //All static
    }

    /**
     * @param args
     * @throws IOException
     * @throws ParseException
     * @throws InconsistantSMSException
     */
    public static void main( final String[] args ) throws IOException, ParseException, InconsistantSMSException
    {
        LOGGER.info( "starting" );

        final String[] csvInFiles = {
            "Export SMS 05 01 2011 14 22[INBOX-Elisabeth].csv",
            "Export SMS 05 01 2011 14 29[ARCH-Elisabeth].csv",
            "Export SMS 05 01 2011 14 30[SENT-Elisabeth].csv"
            };
        final String[] mailInFiles = {
            "Elisabeth"
            };
        final String    outFile = "outputPass1.csv";
        final SMSLoader loader  = new SMSLoader();

        //
        // Read all files
        //
        for( final String fn : csvInFiles ) {
            final File f = new File( fn );

            LOGGER.info( "reading: " + f );

            loader.addFile( f );
        }
        final Charset cs = Charset.forName( "ISO-8859-1" );

        for( final String fn : mailInFiles ) {
            final File f = new File( fn );

            LOGGER.info( "reading: " + f );

            try( MboxFile mbf = new MboxFile( f, cs ) ) {
                final int msgCount = mbf.getMessageCount();

                LOGGER.info( "Messages: " + msgCount );
            }
        }

        loader.removeClones();
        loader.sort();

        loader.save( new File( outFile ) );
    }
}
