package cx.ath.choisnet.tools.phone.contacts.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;
import cx.ath.choisnet.tools.phone.BadFileFormatException;
import cx.ath.choisnet.tools.phone.contacts.AbstractContactPropertiesBuilder;
import cx.ath.choisnet.tools.phone.contacts.ContactValueType;

/**
 *
 *
 */
public class MPECVSContactPropertiesBuilder
    extends	AbstractContactPropertiesBuilder
{
    private final static Logger logger = Logger.getLogger(
            MPECVSContactPropertiesBuilder.class
            );

    /* *
     *
     * @param contactCVSFile
     * @param charset
     * @param csvSeparator
     * @throws IOException
     * @throws BadFileFormatException
     * /
    public MPECVSContactPropertiesBuilder(
        final File 		contactCVSFile,
        final Charset 	charset,
        final char 		csvSeparator
        )
        throws 	IOException,
                BadFileFormatException
    {
        CSVReader csvr = new CSVReader(
                new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream( contactCVSFile ),
                            charset
                            )
                        ),
                        csvSeparator
                        );

        try {
            // Expecting header definition on first line
            String[] line = csvr.readNext();
            logger.trace( "head line length: " + (line==null?-1:line.length) );

            if( line == null ) {
                throw new BadFileFormatException(
                    "Empty file: " + contactCVSFile
                    );
                }
            if( line.length == 1 ) {
                throw new BadFileFormatException(
                    "Can't parse header: " + contactCVSFile
                    );
                }

            // waiting for something like
            // "Titre","Prénom","Nom","Portable","Portable (dom)","Portable (trav)","Domicile","Travail","Fax","Autre","Adresse","CP","Ville","Région","Pays","Adresse (travail)","CP (travail)","Ville (travail)","Région (travail)","Pays (travail)","Info","e-mail","e-mail 2","e-mail 3","Web","Organisation","Anniversaire"
            for( int i = 0; i<line.length; i++ ) {
                final ContactValueType type;
                String           defaultValue	= "";

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
        finally {
            csvr.close();
            }
    }*/

    /**
     *
     * @param csvReader
     * @param contactCVSFile
     * @param charset
     * @param cvsSeparator
     * @throws BadFileFormatException
     * @throws IOException
     */
    public MPECVSContactPropertiesBuilder(
        final CSVReader	csvReader,
        final File 		contactCVSFile,
        final Charset 	charset,
        final char 		cvsSeparator
        )
        throws 	BadFileFormatException,
                IOException
    {
        // Expecting header definition on first line
        String[] line = csvReader.readNext();
        logger.trace( "head line length: " + (line==null?-1:line.length) );

        if( line == null ) {
            throw new BadFileFormatException(
                "Empty file: " + contactCVSFile
                );
            }
        if( line.length == 1 ) {
            logger.error( "head line : " + Arrays.toString( line ) );

            throw new BadFileFormatException(
                "Can't parse header: " + contactCVSFile
                );
            }

        // waiting for something like
        // "Titre","Prénom","Nom","Portable","Portable (dom)","Portable (trav)","Domicile","Travail","Fax","Autre","Adresse","CP","Ville","Région","Pays","Adresse (travail)","CP (travail)","Ville (travail)","Région (travail)","Pays (travail)","Info","e-mail","e-mail 2","e-mail 3","Web","Organisation","Anniversaire"
        for( int i = 0; i<line.length; i++ ) {
            final ContactValueType type;
            String           defaultValue	= "";

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
