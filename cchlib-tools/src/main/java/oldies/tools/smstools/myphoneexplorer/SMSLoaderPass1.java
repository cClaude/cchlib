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
    private static final Logger slogger = Logger.getLogger(SMSLoaderPass1.class);

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
    public static void main( String[] args ) throws IOException, ParseException, InconsistantSMSException
    {
        slogger.info( "starting" );

        String[] csvInFiles = {
            "Export SMS 05 01 2011 14 22[INBOX-Elisabeth].csv",
            "Export SMS 05 01 2011 14 29[ARCH-Elisabeth].csv",
            "Export SMS 05 01 2011 14 30[SENT-Elisabeth].csv"
            };
        String[] mailInFiles = {
            "Elisabeth"
            };
        String    outFile = "outputPass1.csv";
        SMSLoader loader  = new SMSLoader();

        //
        // Read all files
        //
        for( String fn : csvInFiles ) {
            File f = new File( fn );

            slogger.info( "reading: " + f );

            loader.addFile( f );
        }
        Charset cs = Charset.forName( "ISO-8859-1" );

        for( String fn : mailInFiles ) {
            File f = new File( fn );

            slogger.info( "reading: " + f );

            MboxFile mbf = new MboxFile( f, cs );

            int msgCount = mbf.getMessageCount();

            slogger.info( "Messages: " + msgCount );

            mbf.close();
            }

        loader.removeClones();
        loader.sort();

        loader.save( new File( outFile ) );
    }
}
