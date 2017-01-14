package oldies.tools.phone.contacts.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import oldies.tools.phone.BadFileFormatException;
import oldies.tools.phone.contacts.Contact;
import oldies.tools.phone.contacts.ContactProperties;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;

public class MPECVSContactListTestApp
{
    private static final Logger LOGGER = Logger.getLogger( MPECVSContactListTestApp.class );

    private MPECVSContactListTestApp()
    {
        // App
    }

    public static void main( final String[] args ) throws IOException, BadFileFormatException
    {
        //
        final String filename   = "yyyy-mm-dd_Tel-Concats.csv";
        final File   telDirFile = FileHelper.getUserHomeDirectoryFile(
                "My Tel/Contacts"
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
