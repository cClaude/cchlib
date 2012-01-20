package cx.ath.choisnet.tools.phone.contacts.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import com.googlecode.cchlib.io.FileHelper;
import cx.ath.choisnet.tools.phone.BadFileFormatException;
import cx.ath.choisnet.tools.phone.contacts.Contact;
import cx.ath.choisnet.tools.phone.contacts.ContactProperties;

public class MPECVSContactListTest
{
    private final static Logger logger = Logger.getLogger( MPECVSContactListTest.class );

    public static void main(String[]args) throws IOException, BadFileFormatException
    {
        //
        String filename = "2011-12-10_Tel-Concats.csv";

        //String filename = "2012-01-18.ContactsTel[EN].csv";
        File telDirFile = FileHelper.getUserHomeDirFile( "Mes documents\\#Tel\\Contacts" );
        File myPhoneExplorerCVSFile = new File( telDirFile, filename );

        MPECVSContactList contactList
            = new MPECVSContactList(
                    myPhoneExplorerCVSFile,
                    MPECVSContactList.CHARSET,
                    MPECVSContactList.CSV_SEPARATOR
                    );

        ContactProperties cp = contactList.getContactProperties();

        for( int i = 0; i<cp.size(); i++ ) {
            logger.info(
                "H[" + i + "]=\"" + cp.getName( i ) + "\""
                    + " - " + cp.getType( i )
                    + " d=\"" + cp.getDefault( i ) + "\""
                    );
            }

        for( Contact contact : contactList ) {
            for( int i = 0; i<cp.size(); i++ ) {
                logger.info(
                    "C[" + i + "]: "
                        + cp.getName( i )
                        + " = "
                        + contact.getValue( i )
                    );
                }
            }

        logger.info( "size = " + contactList.size() );

        logger.info( "done." );
    }
}
