package oldies.tools.phone.contacts.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import oldies.tools.phone.BadFileFormatException;
import oldies.tools.phone.contacts.AbstractContactPropertiesBuilder;
import oldies.tools.phone.contacts.ContactValueType;
import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;
import com.googlecode.cchlib.lang.StringHelper;

public class MPECVSContactPropertiesBuilder
    extends    AbstractContactPropertiesBuilder
{
    private static final Logger LOG = Logger.getLogger( MPECVSContactPropertiesBuilder.class );

    public MPECVSContactPropertiesBuilder(
        final CSVReader csvReader,
        final File      contactCVSFile,
        final Charset   charset,
        final char      cvsSeparator
        )
        throws BadFileFormatException, IOException
    {
        final String[] line = csvReader.readNext();

        LOG.trace( "head line length: " + (line==null?-1:line.length) );

        if( line == null ) {
            throw new BadFileFormatException(
                "Empty file: " + contactCVSFile
                );
            }

        if( line.length == 1 ) {
            LOG.error( "head line : " + Arrays.toString( line ) );

            throw new BadFileFormatException(
                "Can't parse header: " + contactCVSFile
                );
            }

        // waiting for something like
        // "Titre","Prénom","Nom","Portable","Portable (dom)","Portable (trav)","Domicile","Travail","Fax","Autre","Adresse","CP","Ville","Région","Pays","Adresse (travail)","CP (travail)","Ville (travail)","Région (travail)","Pays (travail)","Info","e-mail","e-mail 2","e-mail 3","Web","Organisation","Anniversaire"
        for( int i = 0; i<line.length; i++ ) {
            final ContactValueType type;
            final String           defaultValue    = StringHelper.EMPTY;

            switch( i ) {
            case 1 :
                type = ContactValueType.FIRST_NAME; // Prénom
                break;
            case 2 :
                type = ContactValueType.LAST_NAME; // Nom
                break;
            case 3 :
            case 4 :
            case 5 :
            case 6 :
            case 7 :
            case 8 :
            case 9 :
                type = ContactValueType.PHONE_NUMBER;
                break;
            case 21 :
            case 22 :
            case 23 :
                type = ContactValueType.EMAIL;
                break;
            case 24 :
                type = ContactValueType.URL;
                break;
            default :
                type = ContactValueType.UNDEFINED;
                break;
                }

            super.setContactProperty(
                i,
                line[ i ],
                type,
                defaultValue
                );
            }
    }
}
