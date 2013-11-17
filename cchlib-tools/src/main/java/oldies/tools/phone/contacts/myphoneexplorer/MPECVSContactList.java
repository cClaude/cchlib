package oldies.tools.phone.contacts.myphoneexplorer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import oldies.tools.phone.BadFileFormatException;
import oldies.tools.phone.contacts.Contact;
import oldies.tools.phone.contacts.ContactList;
import oldies.tools.phone.contacts.ContactProperties;
import oldies.tools.phone.contacts.DefaultContact;
import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;

/**
 * Create a {@link ContactProperties} for a MyPhoneExplorer CVS file
 */
public class MPECVSContactList
    implements ContactList
{
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger( MPECVSContactList.class );

    public static final char CSV_SEPARATOR = ',';
    public static final String CHARSET="UTF8";

    private MPECVSContactProperties contactProperties;
    private List<Contact> contactList = new ArrayList<>();

    /**
     *
     * @param myPhoneExplorerCVSFile
     * @param charsetName
     * @param cvsSeparator
     * @throws IOException
     * @throws BadFileFormatException
     */
    public MPECVSContactList(
            final File         myPhoneExplorerCVSFile,
            final String    charsetName,
            final char         cvsSeparator
            )
            throws IOException, BadFileFormatException
    {
        this(
            myPhoneExplorerCVSFile,
            Charset.forName( charsetName ),
            cvsSeparator
            );
    }

    /**
     *
     * @param myPhoneExplorerCVSFile
     * @param cvsSeparator
     * @throws BadFileFormatException
     * @throws IOException
     */
    @SuppressWarnings("resource")
    public MPECVSContactList(
        final File         myPhoneExplorerCVSFile,
        final Charset    charset,
        final char         cvsSeparator
        )
        throws IOException, BadFileFormatException
    {
        if( LOGGER.isTraceEnabled() ) {
            LOGGER.trace( "File :" + myPhoneExplorerCVSFile );
            LOGGER.trace( "charset :" + charset );
            LOGGER.trace( "cvsSeparator :" + cvsSeparator );
            }
        final CSVReader csvr = new CSVReader(
                new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream( myPhoneExplorerCVSFile ),
                            charset
                            )
                        ),
                        cvsSeparator
                    );

        try {
            this.contactProperties = new MPECVSContactProperties(
                    csvr,
                    myPhoneExplorerCVSFile,
                    charset,
                    cvsSeparator
                    );

            // Expecting header definition on first line
            // Skip header
            String[] line = csvr.readNext();
            LOGGER.trace( "line: " + line );

            if( line == null ) {
                throw new BadFileFormatException( myPhoneExplorerCVSFile, 1 );
                }

            int lineNumber = 1;

            for(;;) {
                lineNumber++;
                line = csvr.readNext();

                if( line == null ) {
                    break; // EOF
                    }
                if( line.length != this.contactProperties.size() ) {
                    throw new BadFileFormatException( myPhoneExplorerCVSFile, lineNumber );
                    }

                addContactFromLine( line );
                }
            }
        finally {
            csvr.close();
            }
    }

    private void addContactFromLine( String[] line )
    {
        Contact c = new DefaultContact(contactProperties);

        for( int i = 0; i<line.length; i++ ) {
            c.setValue( i, line[ i ] );
            }

        add( c );
    }

    @Override
    public Iterator<Contact> iterator()
    {
        return this.contactList.iterator();
    }

    @Override
    public int size()
    {
        return this.contactList.size();
    }

    @Override
    public ContactProperties getContactProperties()
    {
        return this.contactProperties;
    }

    @Override
    public void add( final Contact contact )
    {
        this.contactList.add( contact );
    }

    @Override
    public boolean remove( final Contact contact )
    {
        return this.contactList.remove( contact );
    }
}
