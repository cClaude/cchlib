package oldies.tools.smstools.myphoneexplorer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import oldies.tools.smstools.myphoneexplorer.thunderbird.MboxFile;
import org.apache.log4j.Logger;

public class SMSLoaderPass1
{
    private static final Logger LOGGER = Logger.getLogger(SMSLoaderPass1.class);

    private final SMSLoader loader  = new SMSLoader();
    private final Charset charset;

    private SMSLoaderPass1( final Charset charset )
    {
        this.charset = charset;
    }

    private void loadCSVIn( final String[] csvInFiles ) throws IOException, ParseException
    {
        for( final String filename : csvInFiles ) {
            final File file = new File( filename );

            LOGGER.info( "reading: " + file );

            this.loader.addFile( file );
        }
    }

    private void loadMailIn( final String[] mailInFiles )
        throws FileNotFoundException, IOException
    {
        for( final String filename : mailInFiles ) {
            final File file = new File( filename );

            LOGGER.info( "reading: " + file );

            try( MboxFile mbf = new MboxFile( file, this.charset ) ) {
                final int msgCount = mbf.getMessageCount();

                LOGGER.info( "Messages: " + msgCount );
            }
        }
    }

    private void save( final File file ) throws IOException
    {
        this.loader.save( file );
    }

    private void cleanUp() throws InconsistantSMSException
    {
        this.loader.removeClones();
        this.loader.sort();
    }

    public static void run(
        final String[] csvInFiles,
        final String[] mailInFiles,
        final String   outFile,
        final Charset  charset
        ) throws IOException, ParseException, InconsistantSMSException
    {
        LOGGER.info( "starting" );

        final SMSLoaderPass1 pass1 = new SMSLoaderPass1( charset );

        //
        // Read all files
        //
        pass1.loadCSVIn( csvInFiles );
        pass1.loadMailIn( mailInFiles );

        pass1.cleanUp();
        pass1.save( new File( outFile ) );
    }

    public static void main( final String[] args ) throws Exception
    {
        final String[] csvInFiles = {
            "Export SMS YYYY-MM-DD [INBOX-Xyz].csv",
            "Export SMS YYYY-MM-DD [ARCH-Xyz].csv",
            "Export SMS YYYY-MM-DD [SENT-Xyz].csv"
            };
        final String[] mailInFiles = {
            "Xyz"
            };
        final String  outFile = "outputPass1.csv";

        run( csvInFiles, mailInFiles, outFile, Charset.forName( "ISO-8859-1" ) );
    }
}
