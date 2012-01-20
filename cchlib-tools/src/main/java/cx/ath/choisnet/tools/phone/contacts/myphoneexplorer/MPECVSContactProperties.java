package cx.ath.choisnet.tools.phone.contacts.myphoneexplorer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import au.com.bytecode.opencsv.CSVReader;

import cx.ath.choisnet.tools.phone.BadFileFormatException;
import cx.ath.choisnet.tools.phone.contacts.ContactProperties;
import cx.ath.choisnet.tools.phone.contacts.DefaultContactProperties;

/**
 * Create a {@link ContactProperties} for a MyPhoneExplorer CVS file
 */
public class MPECVSContactProperties
    extends DefaultContactProperties
        implements ContactProperties
{
    private static final long serialVersionUID = 1L;

    /* *
     *
     * @param myPhoneExplorerCVSFile
     * @param charset
     * @param cvsSeparator
     * @throws BadFileFormatException
     * @throws IOException
     * /
    public MPECVSContactProperties(
        final File 		myPhoneExplorerCVSFile,
        final Charset 	charset,
        final char 		cvsSeparator
        )
        throws IOException, BadFileFormatException
    {
        super( new MPECVSContactPropertiesBuilder(
                myPhoneExplorerCVSFile,
                charset,
                cvsSeparator
                ) );
    }*/

    /**
     *
     * @param csvReader
     * @param cvsFile
     * @param charset
     * @param cvsSeparator
     * @throws IOException
     * @throws BadFileFormatException
     */
    public MPECVSContactProperties(
        final CSVReader	csvReader,
        final File 		cvsFile,
        final Charset 	charset,
        final char 		cvsSeparator
        )
        throws 	BadFileFormatException,
                IOException
    {
        super( new MPECVSContactPropertiesBuilder(
                csvReader,
                cvsFile,
                charset,
                cvsSeparator
                ) );
    }
}
