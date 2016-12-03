package oldies.tools.phone.contacts.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import oldies.tools.phone.BadFileFormatException;
import oldies.tools.phone.contacts.Contact;
import oldies.tools.phone.contacts.ContactProperties;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;

public class MPECVSContactListTest
{
    private static final Logger LOGGER = Logger.getLogger( MPECVSContactListTest.class );

    public static void main( final String[] args ) throws IOException, BadFileFormatException
    {
        //
        final String filename = "2011-12-10_Tel-Concats.csv";

        //String filename = "2012-01-18.ContactsTel[EN].csv";
        final File telDirFile = FileHelper.getUserHomeDirectoryFile(
                "Mes documents\\#Tel\\Contacts"
                );
        final File myPhoneExplorerCVSFile = new File( telDirFile, filename );

        final MPECVSContactList contactList
            = new MPECVSContactList(
                    myPhoneExplorerCVSFile,
                    MPECVSContactList.CHARSET,
                    MPECVSContactList.CSV_SEPARATOR
                    );

        final ContactProperties cp = contactList.getContactProperties();

        for( int i = 0; i<cp.size(); i++ ) {
            LOGGER.info(
                "H[" + i + "]=\"" + cp.getName( i ) + "\""
                    + " - " + cp.getType( i )
                    + " d=\"" + cp.getDefault( i ) + "\""
                    );
            }

        for( final Contact contact : contactList ) {
            for( int i = 0; i<cp.size(); i++ ) {
                LOGGER.info(
                    "C[" + i + "]: "
                        + cp.getName( i )
                        + " = "
                        + contact.getValue( i )
                    );
                }
            }

        LOGGER.info( "size = " + contactList.size() );

        LOGGER.info( "done." );
    }
}
